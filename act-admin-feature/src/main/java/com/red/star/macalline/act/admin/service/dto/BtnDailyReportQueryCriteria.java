package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Data
public class BtnDailyReportQueryCriteria {

    // 模块名称
    private String moduleName;

    private String dictIdStr;

    private String dictIdStrSummary;

    // 数据日期
    private String dataDateStart;

    // 数据日期
    private String dataDateEnd;

    // 活动标识
    private String source;
}