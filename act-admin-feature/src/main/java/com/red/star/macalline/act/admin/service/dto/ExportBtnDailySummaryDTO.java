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
public class ExportBtnDailySummaryDTO implements Serializable {

    // 主键ID
    private Integer id;

    // 扩展字段
    private String ext1;

    // 展示图片
    private Long pv;

    // 模块链接地址
    private Long uv;

    private String dictLabel;
}