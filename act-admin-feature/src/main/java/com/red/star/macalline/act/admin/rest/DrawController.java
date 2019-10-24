package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.domain.bo.FlopBo;
import com.red.star.macalline.act.admin.domain.bo.LuckyBo;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.DrawVO;
import com.red.star.macalline.act.admin.domain.vo.FlopVo;
import com.red.star.macalline.act.admin.domain.vo.LuckyData;
import com.red.star.macalline.act.admin.service.DrawService;
import io.swagger.annotations.Api;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

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
public class DrawController {

    @Resource
    private DrawService drawService;

    @GetMapping("/{actCode}/draw")
    @ResponseBody
    public ActResponse getDrawMatrix(@PathVariable("actCode") String actCode, String drawID) {
        DrawVO drawVO = drawService.drawInfo(actCode, drawID);
        return ActResponse.buildSuccessResponse(drawVO);
    }

    @PostMapping("/{actCode}/draw/add")
    @ResponseBody
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

    @PostMapping("/{actCode}/draw/save")
    @ResponseBody
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

    @GetMapping("/{actCode}/draw/list")
    @ResponseBody
    public ActResponse getDrawList(@PathVariable("actCode") String actCode) {
        return drawService.getDrawList(actCode);
    }

    @GetMapping("/{actCode}/draw/delete")
    @ResponseBody
    public ActResponse deleteDraw(@PathVariable("actCode") String actCode, String drawID) {
        return drawService.deleteDraw(actCode, drawID);
    }

    /**
     * 翻牌抽奖
     *
     * @param flopBo
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/analysisFlopData")
    public ActResponse<FlopVo> analysisFlopData(@RequestBody @Valid FlopBo flopBo) {
        List<FlopVo> list = drawService.analysisFlopData(flopBo);
        return ActResponse.buildSuccessResponse(list);
    }

    /**
     * 中奖数据
     *
     * @param luckyBo
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/analysisLuckyData")
    public ActResponse<LuckyData> analysisLuckyData(@RequestBody @Valid LuckyBo luckyBo) {
        LuckyData luckyData = drawService.analysisLuckyData(luckyBo);
        return ActResponse.buildSuccessResponse(luckyData);
    }
}
