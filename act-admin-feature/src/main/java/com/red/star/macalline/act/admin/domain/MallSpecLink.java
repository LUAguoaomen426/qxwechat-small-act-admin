package com.red.star.macalline.act.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_wap_act_mall_spec_merge")
@TableName(value = "tb_wap_act_mall_spec_merge")
@NoArgsConstructor
@AllArgsConstructor
public class MallSpecLink implements Serializable {

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
     * 活动code
     */
    @ApiModelProperty(value = "活动code")
    @Column(name = "act_code")
    private String actCode;

    /**
     * 广告位code
     */
    @ApiModelProperty(value = "广告位code")
    @Column(name = "spec_code")
    private String specCode;


    /**
     * 是否展示
     */
    @ApiModelProperty(value = "是否展示")
    @Column(name = "is_show")
    private Boolean isShow;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Timestamp updateTime;


}