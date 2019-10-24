package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.DrawVO;
import com.red.star.macalline.act.admin.service.DrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.rest
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-24 10:59
 * @Version: 1.0
 */
@Api(tags = "抽奖管理")
@RestController
@RequestMapping("api")
public class ActDrawController {

    @Resource
    private DrawService drawService;

    @Log("获取抽奖信息")
    @ApiOperation(value = "获取抽奖信息")
    @GetMapping("/{actCode}/draw")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ALL_DRAW_ALL','ALL_DRAW_INFO_LIST')")
    public ActResponse getDrawMatrix(@PathVariable("actCode") String actCode, String drawID) {
        DrawVO drawVO = drawService.drawInfo(actCode, drawID);
        return ActResponse.buildSuccessResponse(drawVO);
    }

    @Log("新增抽奖信息")
    @ApiOperation(value = "新增抽奖信息")
    @PostMapping("/{actCode}/draw/add")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ALL_DRAW_ALL','ALL_DRAW_INFO_SAVE')")
    public ActResponse newDraw(@PathVariable("actCode") String actCode, @RequestBody @Validated DrawVO drawVo, BindingResult result) {
        if (result.hasErrors()) {
            return ActResponse.buildErrorResponse(result.getFieldError().getDefaultMessage());
        }
        if (drawVo.getDrawStartTime().compareTo(drawVo.getDrawEndTime()) != -1) {
            return ActResponse.buildErrorResponse("draw time range is wrong");
        }
        ActResponse actResponse = drawService.newlyDrawInfo(actCode, drawVo);
        return actResponse;
    }

    @Log("新增抽奖概率")
    @ApiOperation(value = "新增抽奖概率")
    @PostMapping("/{actCode}/draw/save")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ALL_DRAW_ALL','ALL_DRAW_SAVE')")
    public ActResponse saveDraw(@PathVariable("actCode") String actCode, @RequestBody @Validated DrawVO drawVo, BindingResult result) throws ParseException {
        if (result.hasErrors()) {
            return ActResponse.buildErrorResponse(result.getFieldError().getDefaultMessage());
        }
        if (drawVo.getDrawStartTime().compareTo(drawVo.getDrawEndTime()) != -1) {
            return ActResponse.buildErrorResponse("draw time range is wrong");
        }
        ActResponse actResponse = drawService.saveDrawInfo(actCode, drawVo);
        return actResponse;
    }

    @Log("获取抽奖概率")
    @ApiOperation(value = "获取抽奖概率")
    @GetMapping("/{actCode}/draw/list")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ALL_DRAW_ALL','ALL_DRAW_LIST')")
    public ActResponse getDrawList(@PathVariable("actCode") String actCode) {
        return drawService.getDrawList(actCode);
    }

    @Log("删除抽奖信息")
    @ApiOperation(value = "删除抽奖信息")
    @GetMapping("/{actCode}/draw/delete")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ALL_DRAW_ALL','ALL_DRAW_INFO_DELETE')")
    public ActResponse deleteDraw(@PathVariable("actCode") String actCode, String drawID) {
        return drawService.deleteDraw(actCode, drawID);
    }


}
