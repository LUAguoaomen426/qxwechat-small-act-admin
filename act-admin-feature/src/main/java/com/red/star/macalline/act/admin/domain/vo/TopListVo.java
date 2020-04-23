package com.red.star.macalline.act.admin.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author chenjunliang
 * @date 2020/4/17
 * @time 10:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopListVo {

    @NotBlank
    @ApiModelProperty("榜单")
    private String billboardNo;

    @NotBlank
    @ApiModelProperty("商品")
    private String goodsNo;

    @NotNull
    @ApiModelProperty("榜单值")
    private Integer billboardNum;
}
