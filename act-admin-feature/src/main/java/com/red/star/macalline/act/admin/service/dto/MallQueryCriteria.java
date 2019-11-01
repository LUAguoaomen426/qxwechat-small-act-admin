package com.red.star.macalline.act.admin.service.dto;

import com.red.star.macalline.act.admin.annotation.Query;
import lombok.Data;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Data
public class MallQueryCriteria {

    /**
     * 多字段模糊
     */
    @Query(blurry = "omsCode,mallCode,mallName")
    private String blurry;
}