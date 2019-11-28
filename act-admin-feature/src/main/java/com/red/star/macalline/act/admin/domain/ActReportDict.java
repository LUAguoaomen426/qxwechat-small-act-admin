package com.red.star.macalline.act.admin.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_report_act_dict")
@TableName(value = "tb_report_act_dict")
public class ActReportDict implements Serializable {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 创建时间
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    // 活动标识
    @Column(name = "source", nullable = false)
    private String source;

    // 名称
    @Column(name = "label", nullable = false)
    private String label;

    // 值
    @Column(name = "value", nullable = false)
    private String value;

    // 扩展字段1
    @Column(name = "ext_1")
    private String ext_1;

    // 模块链接地址
    @Column(name = "pid")
    private Integer pid;

    public void copy(ActReportDict source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}