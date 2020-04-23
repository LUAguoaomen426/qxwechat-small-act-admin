package com.red.star.macalline.act.admin.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author chenjunliang
 * @date 2020/4/17
 * @time 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo {
    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "归属榜单编号")
    private String billboardNo;

    @ApiModelProperty(value = "归属品牌编号")
    private String brandNo;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "背景图")
    private String bgImageUrl;

    @ApiModelProperty(value = "顺序")
    private Integer order;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
