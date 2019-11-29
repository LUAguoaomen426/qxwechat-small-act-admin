package com.red.star.macalline.act.admin.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2019/11/14 11:04
 * @Created by Akari
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpQueryCriteria {
    private String name;
    private String mobile;
    private String mallCondition;
    private String type;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    private Integer cliType;
    private String scene;
}
