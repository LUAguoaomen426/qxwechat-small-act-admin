package com.red.star.macalline.act.admin.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019/8/1 17:20
 * @Created by Akari
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawVO {

    @NotNull
    private String id;

    @NotNull(message = "start time is empty")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date drawStartTime;

    @NotNull(message = "end time is empty")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date drawEndTime;

    @NotNull(message = "prizeCount Compulsory field")
    @Min(value = 1,message = "prizeCount min Value is 1")
    private int prizeCount;
    @NotNull(message = "prizeCode Compulsory field")
    private List<String> prizeCode;
    @NotNull(message = "matrix is empty")
    private List<Map<String,Object>> tableDraw;
}
