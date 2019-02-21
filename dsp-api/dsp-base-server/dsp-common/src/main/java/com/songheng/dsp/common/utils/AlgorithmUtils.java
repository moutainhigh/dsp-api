package com.songheng.dsp.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 10:55
 * @description: JDK 算法工具类
 */
public class AlgorithmUtils {

    /**
     * SHA -安全散列算法
     */
    private static final String KEY_SHA = "SHA";
    /**
     * MD5 -消息摘要算法
     */
    private static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     * 散列消息鉴别码
     *
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     *
     */
    private static final String KEY_MAC = "HmacMD5";

    /**
     * ALGORITHM 算法
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     *
     * DES                  key size must be equal to 56
     * DESede(TripleDES)     key size must be equal to 112 or 168
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish          key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2                  key size must be between 40 and 1024 bits
     * RC4(ARCFOUR)      key size must be between 40 and 1024 bits
     *
     */
    public static final String ALGORITHM = "DESede";


    /**
     * 支持以下任意一种算法
     *
     *
     * PBEWithMD5AndDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     *
     */
    private static final String PEB_ALGORITHM = "PBEWithMD5AndDES";



    static{
        try {
            //根据 算法类型 自动生成密匙
            String key = AlgorithmUtils.initKey(String.valueOf(System.currentTimeMillis()),ALGORITHM);
            //DESede 加密
            AlgorithmUtils.encrypt("1234".getBytes("UTF-8"), key);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Base64解码
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * Base64编码
     * @param input
     * @return
     */
    public static String base64Encode(byte[] input){
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符�?,/=转为其他字符, 见RFC3548).
     */
    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }


    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(base64Decode(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }

    /**
     * 转换密钥<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {

        //使用 DES 对称算法时，使用如下注释代码
//        DESKeySpec dks = new DESKeySpec(key);
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
//        SecretKey secretKey = keyFactory.generateSecret(dks);

        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

        return secretKey;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(base64Decode(key));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(base64Decode(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public static String initKey(String algorithmType) throws Exception {
        return initKey(null,algorithmType);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static String initKey(String seed,String algorithmType) throws Exception {

        SecureRandom secureRandom = StringUtils.isNotBlank(seed) ?
                new SecureRandom(base64Decode(seed)) : new SecureRandom();

        KeyGenerator kg = KeyGenerator.getInstance(algorithmType);
        kg.init(secureRandom);

        SecretKey secretKey = kg.generateKey();

        return base64UrlSafeEncode(secretKey.getEncoded());
    }


    /**
     * salt 初始化
     * 指定为8位的salt （salt就是干扰码，通过添加干扰码增加安全）
     *
     * @return
     * @throws Exception
     */
    public static byte[] initSalt() throws Exception {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 转换密钥 PBE
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static Key toKey(String password) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PEB_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        return secretKey;
    }

    /**
     * 加密
     *
     * @param data 数据
     * @param password 密码
     * @param salt  盐
     * @return
     * @throws Exception
     */
    public static byte[] encryptPBE(byte[] data, String password, byte[] salt)
            throws Exception {
        Key key = toKey(password);
        //参数规范，第一个参数是SALT，第二个是迭代次数（经过散列函数多次迭代）
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(PEB_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        return cipher.doFinal(data);

    }

    /**
     * 解密
     *
     * @param data  数据
     * @param password 密码
     * @param salt  盐
     * @return
     * @throws Exception
     */
    public static byte[] decryptPBE(byte[] data, String password, byte[] salt)
            throws Exception {
        Key key = toKey(password);
        //参数规范，第一个参数是SALT，第二个是迭代次数（经过散列函数多次迭代）
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(PEB_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        return cipher.doFinal(data);

    }
}
