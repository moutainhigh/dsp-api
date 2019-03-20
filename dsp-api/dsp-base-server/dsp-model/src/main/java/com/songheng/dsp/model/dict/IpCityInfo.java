package com.songheng.dsp.model.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 10:40
 * @description: IpCity
 */
@Getter
@Setter
@ToString
public class IpCityInfo implements Serializable {

    private static final long serialVersionUID = 5382282321369508453L;

    private String ip;
    private String province;
    private String city;

}
