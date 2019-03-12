package com.songheng.dsp.common.hbase;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.HbaseFamilyQualifierEnum;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/7 17:50
 * @description:
 */
public class HbaseUtilTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getTest(){
        Get totGet = new Get(new StringBuilder("190304203556640").reverse().toString().getBytes());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_CURRENTCLICK.getFmlyQlfirName());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_CURRENTSHOW.getFmlyQlfirName());
        Map<String, byte[]> map = HbaseUtil.get(ClusterEnum.CLUSTER_B, PropertyPlaceholder.getProperty("adplatform_adstatus_B"), totGet);
        for (String key : map.keySet()){
            System.out.println(key+" : "+Bytes.toLong(map.get(key)));
        }
    }

    @Test
    public void getListTest(){
        List<Get> getlist = new ArrayList<Get>();
        Get totGet = new Get(new StringBuilder("190304203556640").reverse().toString().getBytes());
        Get dayGet = new Get(new StringBuilder("190304203556640").reverse().append(new StringBuilder("20190307").reverse()).toString().getBytes());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_CURRENTCLICK.getFmlyQlfirName());
        totGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_CURRENTSHOW.getFmlyQlfirName());
        dayGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());
        dayGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_CLICK.getFmlyQlfirName());
        dayGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_SHOW.getFmlyQlfirName());
        getlist.add(totGet);
        getlist.add(dayGet);
        Map<String, byte[]> map = HbaseUtil.get(ClusterEnum.CLUSTER_B, PropertyPlaceholder.getProperty("adplatform_adstatus_B"), getlist);
        for (String key : map.keySet()){
            System.out.println(key+" : "+Bytes.toLong(map.get(key)));
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
