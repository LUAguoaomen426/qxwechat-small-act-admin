package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Data
public class BtnDailyReportQueryCriteria {

    // 模块名称
    private String moduleName;

    private Long dictId;

    // 数据日期
    private Date DataDateStart;

    // 数据日期
    private Date DataDateEnd;

    // 活动标识
    private String source;
}