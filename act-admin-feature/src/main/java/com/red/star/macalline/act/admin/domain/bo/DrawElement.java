package com.red.star.macalline.act.admin.domain.bo;

import lombok.Data;

/**
 * @Description TODO
 * @Date 2019/8/1 15:55
 * @Created by Akari
 */
@Data
public class DrawElement {
    private int prizeCount;
    private String prizeProbability;
    private int drawValueHead;
    private int drawValueEnd;
}
