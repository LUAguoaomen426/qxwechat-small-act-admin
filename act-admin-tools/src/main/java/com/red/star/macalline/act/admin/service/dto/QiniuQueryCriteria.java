package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;
import com.red.star.macalline.act.admin.annotation.Query;

/**
 * @author Zheng Jie
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiniuQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;
}
