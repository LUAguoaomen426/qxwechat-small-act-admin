package com.red.star.macalline.act.admin.modules.system.service.dto;

import lombok.Data;
import com.red.star.macalline.act.admin.annotation.Query;

/**
 * 公共查询类
 */
@Data
public class PermissionQueryCriteria {

    // 多字段模糊
    @Query(blurry = "name,alias")
    private String blurry;
}
