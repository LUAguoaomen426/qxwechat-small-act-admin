package com.red.star.macalline.act.admin.modules.system.service.dto;

import lombok.Data;
import com.red.star.macalline.act.admin.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}