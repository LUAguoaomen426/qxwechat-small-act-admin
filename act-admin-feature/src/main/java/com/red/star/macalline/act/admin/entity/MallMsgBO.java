package com.red.star.macalline.act.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 用于接收WxinfoUtil.getMallMessageApi 获得的商场数据对象中部分商场信息
 * @Date 2019/9/25 10:19
 * @Created by Akari
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MallMsgBO {
    /**
     * 商场详细地址
     */
    @JsonProperty("ADDRESS")
    private String address;
    /**
     * 商场名称
     */
    @JsonProperty("MALL_NAME")
    private String mallName;
    /**
     * 省
     */
    @JsonProperty("PROVINCE")
    private String province;
    /**
     * 城市
     */
    @JsonProperty("CITY")
    private String city;
    /**
     * 商场类型 自营 委管
     */
    @JsonProperty("MALL_TYPE")
    private String mallType;

    @JsonProperty("MALL_CODE")
    private String mallCode;
    @JsonProperty("OMS_CODE")
    private String omsCode;
    @JsonProperty("STATUS")
    private String status;
}
