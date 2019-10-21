package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;
import com.red.star.macalline.act.admin.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageQueryCriteria{

    // 模糊
    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;
}