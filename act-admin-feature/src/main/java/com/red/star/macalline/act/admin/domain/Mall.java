package com.red.star.macalline.act.admin.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_wap_mall")
@TableName(value = "tb_wap_mall")
@NoArgsConstructor
@AllArgsConstructor
public class Mall implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", example = "0")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * OMS商场编码
     */
    @ApiModelProperty(value = "OMS商场编码")
    @Column(name = "oms_code")
    private String omsCode;

    /**
     * 商场编码
     */
    @ApiModelProperty(value = "商场编码")
    @Column(name = "mall_code")
    private String mallCode;

    /**
     * 商场名称
     */
    @ApiModelProperty(value = "商场名称")
    @Column(name = "mall_name")
    private String mallName;

    /**
     * 商场所在省
     */
    @ApiModelProperty(value = "商场所在省")
    @Column(name = "province")
    private String province;

    /**
     * 商场所在城市
     */
    @ApiModelProperty(value = "商场所在城市")
    @Column(name = "city")
    private String city;
    /**
     * 商场详细地址
     */
    @ApiModelProperty(value = "商场详细地址")
    @Column(name = "detail_address")
    private String detailAddress;
    /**
     * 是否自营
     */
    @ApiModelProperty(value = "是否自营商场")
    @Column(name = "self_flag")
    private Boolean selfFlag;
    /**
     * 是否为城市默认商场
     */
    @ApiModelProperty(value = "是否为城市默认商场")
    @Column(name = "default_enable")
    private Boolean defaultEnable;

    /**
     * 活动列表可见
     */
    @Column(name = "entrance_enable")
    private Boolean entranceEnable;
    /**
     * 默认参与大促活动标识
     */
    @Column(name = "defult_join")
    private Boolean defultJoin;
    /**
     * 是否参与活动
     */
    @Transient
    @TableField(exist = false)
    private Boolean isJoin;
    /**
     * 特殊链接是否展示,特殊链接->[商场]时使用
     */
    @Transient
    @TableField(exist = false)
    private Boolean linkShow;
    /**
     * 是否瞄零
     */
    @ApiModelProperty(value = "是否瞄零")
    @Column(name = "is_ml")
    private Boolean isMl;

    public void copy(Mall source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}