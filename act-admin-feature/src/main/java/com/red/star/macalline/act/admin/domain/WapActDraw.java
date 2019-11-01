package com.red.star.macalline.act.admin.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2019/8/19 10:25
 * @Created by Akari
 */
@Data
@Table(name = "tb_wap_act_draw")
@TableName(value = "tb_wap_act_draw")
public class WapActDraw implements Serializable {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", example = "0")
    @TableId(type = IdType.AUTO)
    private Integer id;

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

    private String drawId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer prizeCount;
    private String ticketId;
    private String martix;
    private String actCode;
}
