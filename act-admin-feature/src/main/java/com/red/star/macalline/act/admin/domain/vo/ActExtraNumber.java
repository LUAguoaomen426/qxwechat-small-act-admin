package com.red.star.macalline.act.admin.domain.vo;

import lombok.Data;

/**
 * @author jack
 * @date 2018/11/30
 */
@Data
public class ActExtraNumber {
    private Integer minPrice;
    private Integer maxPrice;
    private Integer lowMinNumber;
    private Integer lowMaxNumber;
    private Integer midMinNumber;
    private Integer midMaxNumber;
    private Integer highMinNumber;
    private Integer highMaxNumber;
}
