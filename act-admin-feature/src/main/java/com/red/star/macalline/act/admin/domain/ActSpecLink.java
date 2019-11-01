package com.red.star.macalline.act.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Description 活动特殊链接实体
 * @Date 2019/6/4 15:19
 * @Created by Akari
 */
@Data
@Table(name = "tb_wap_act_spec_link")
@TableName(value = "tb_wap_act_spec_link")
public class ActSpecLink implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", example = "0")
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 创建时间
    @Column(name = "create_time")
    private Date createTime;

    // 更新时间
    @Column(name = "update_time")
    private Date updateTime;

    /** 链接CODE */
    private String specCode;
    /** 特殊链接名称 */
    private String name;
    /** 链接URL */
    private String url;
    /** 广告位图片链接 */
    private String showImage;
    /** 排序 */
    private Integer sort;
    /** 类型 0为广告位 1为特殊链接 */
    private Integer type;
    /** 特殊链接对应的商场信息 */
    @Transient
    @TableField(exist = false)
    private List<Mall> mallList;
    @Transient
    @TableField(exist = false)
    private List<Mall> changeMallList;
    /** 在商场下是否展示此链接 */
    @Transient
    @TableField(exist = false)
    private Boolean isShow;
    /** 1:上移 2:下移 3:置顶 4:置低 */
    @Transient
    @TableField(exist = false)
    private Integer operate;
    /** 被迫移动的广告位 */
    @Transient
    @TableField(exist = false)
    private String moveSpecCode;
    /** 广告位时间限制JSON格式 */
    private String timeLimit;
    @Transient
    @TableField(exist = false)
    private List<String> time;
    /** 是否有时间限制 */
    @Transient
    @TableField(exist = false)
    private String haveTL;
}
