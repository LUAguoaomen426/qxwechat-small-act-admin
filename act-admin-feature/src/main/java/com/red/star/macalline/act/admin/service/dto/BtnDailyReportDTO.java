package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Data
public class BtnDailyReportDTO implements Serializable {

    // 主键ID
    private Integer id;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 活动标识
    private String source;

    // 数据日期
    private Date DataDate;

    // 模块名称
    private String moduleName;

    // 扩展字段
    private String ext1;

    // 展示图片
    private Long pv;

    // 模块链接地址
    private Long uv;

    private Long dictId;

    private String dictLabel;
}