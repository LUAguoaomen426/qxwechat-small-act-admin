package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Data
public class MallDTO implements Serializable {

    // 自增主键
    private Integer id;

    // 商场编码
    private String omsCode;

    // 商场编码
    private String mallCode;

    // 商场名称
    private String mallName;

    // 省份
    private String province;

    // 城市
    private String city;

    // 自营商场标识
    private Integer selfFlag;

    // 活动列表商场启用状态
    private Integer entranceEnable;

    // 是否为城市默认商场
    private Integer defaultEnable;

    // 是否参与活动的默认值 0不参与 1 参与
    private Integer defultJoin;

    // 详细地址
    private String detailAddress;

    // 是否为瞄零商场 0不是 1是
    private Integer isMl;
}