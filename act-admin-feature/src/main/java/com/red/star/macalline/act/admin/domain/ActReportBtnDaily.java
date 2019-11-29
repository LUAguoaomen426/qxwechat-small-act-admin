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
import java.util.Date;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_report_act_btn_daily")
@TableName(value = "tb_report_act_btn_daily")
public class ActReportBtnDaily implements Serializable {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 创建时间
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time", nullable = false)
    private Timestamp updateTime;

    // 活动标识
    @Column(name = "source", nullable = false)
    private String source;

    // 数据日期
    @Column(name = "data_date", nullable = false)
    private Date DataDate;

    // 模块名称
    @Column(name = "module_name", nullable = false)
    private String moduleName;

    // 展示图片
    @Column(name = "pv")
    private Long pv;

    // 模块链接地址
    @Column(name = "uv")
    private Long uv;

    public void copy(ActReportBtnDaily source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}