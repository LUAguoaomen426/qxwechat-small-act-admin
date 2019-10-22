package com.red.star.macalline.act.admin.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_wap_mall")
public class Mall implements Serializable {

    // 自增主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 商场编码
    @Column(name = "oms_code", unique = true, nullable = false)
    private String omsCode;

    // 商场编码
    @Column(name = "mall_code", unique = true, nullable = false)
    private String mallCode;

    // 商场名称
    @Column(name = "mall_name")
    private String mallName;

    // 省份
    @Column(name = "province")
    private String province;

    // 城市
    @Column(name = "city")
    private String city;

    // 自营商场标识
    @Column(name = "self_flag")
    private Integer selfFlag;

    // 活动列表商场启用状态
    @Column(name = "entrance_enable", nullable = false)
    private Integer entranceEnable;

    // 是否为城市默认商场
    @Column(name = "default_enable", nullable = false)
    private Integer defaultEnable;

    // 是否参与活动的默认值 0不参与 1 参与
    @Column(name = "defult_join")
    private Integer defultJoin;

    // 详细地址
    @Column(name = "detail_address")
    private String detailAddress;

    // 是否为瞄零商场 0不是 1是
    @Column(name = "is_ml")
    private Integer isMl;

    public void copy(Mall source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}