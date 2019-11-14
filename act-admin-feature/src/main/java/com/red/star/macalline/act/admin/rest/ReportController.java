package com.red.star.macalline.act.admin.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.SignUpForm;
import com.red.star.macalline.act.admin.domain.bo.FlopBo;
import com.red.star.macalline.act.admin.domain.bo.LuckyBo;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.*;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.DrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.rest
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-24 18:12
 * @Version: 1.0
 */
@Api(tags = "报表")
@RestController
@RequestMapping("api")
public class ReportController {

    @Autowired
    private ActModuleService actModuleService;

    @Resource
    private DrawService drawService;

    @Log("活动pv、uv")
    @ApiOperation(value = "活动pv、uv")
    @PreAuthorize("hasAnyRole('ADMIN','REPORT_ALL','REPORT_ACT_PVUV')")
    @PostMapping(value = "/analysisPVUVData")
    public ActResponse<SourcePvUvVo> analysisPVUVData(@RequestBody @Valid SourcePvUvBo sourcePvUvBo) {
        List<SourcePvUvVo> list = actModuleService.analysisPVUVData(sourcePvUvBo);
        return ActResponse.buildSuccessResponse(list);
    }

    @Log("参团/单品券人数管理")
    @ApiOperation(value = "参团/单品券人数管理")
    @PreAuthorize("hasAnyRole('ADMIN','REPORT_ALL','REPORT_ACT_GROUP_LIST')")
    @GetMapping("/number")
    public ActResponse number(String source) {
        return actModuleService.number(source);
    }

    @Log("添加额外人数")
    @ApiOperation(value = "添加额外人数")
    @PreAuthorize("hasAnyRole('ADMIN','REPORT_ALL','REPORT_ACT_GROUP_ADD')")
    @PostMapping("/addGroupNumber")
    public ActResponse addGroupNumber(String source, Integer addGroupNumber) {
        actModuleService.addGroupNumber(source, addGroupNumber);
        return ActResponse.buildSuccessResponse();
    }

    @Log("添加单品券额外人数")
    @ApiOperation(value = "添加单品券额外人数")
    @PreAuthorize("hasAnyRole('ADMIN','REPORT_ALL','REPORT_ACT_TICKET_ADD')")
    @PostMapping("/addTicketNumber")
    public ActResponse addTicketNumber(String source,@RequestBody @Valid ActExtraNumber actExtraNumber) throws IOException {
        actModuleService.changeTicketNumber(source, actExtraNumber);
        return ActResponse.buildSuccessResponse();
    }

    @Log("获取抽奖数据")
    @ApiOperation(value = "获取抽奖数据")
    @PostMapping(value = "/analysisFlopData")
    @PreAuthorize("hasAnyRole('ADMIN','DRAW_ALL','REPORT_DRAW_ANALYSIS_FLOP')")
    public ActResponse<FlopVo> analysisFlopData(@RequestBody @Valid FlopBo flopBo) {
        List<FlopVo> list = drawService.analysisFlopData(flopBo);
        return ActResponse.buildSuccessResponse(list);
    }

    @Log("获取中奖数据")
    @ApiOperation(value = "获取中奖数据")
    @PostMapping(value = "/analysisLuckyData")
    @PreAuthorize("hasAnyRole('ADMIN','DRAW_ALL','REPORT_DRAW_ANALYSIS_LUCKY')")
    public ActResponse<LuckyData> analysisLuckyData(@RequestBody @Valid LuckyBo luckyBo) {
        LuckyData luckyData = drawService.analysisLuckyData(luckyBo);
        return ActResponse.buildSuccessResponse(luckyData);
    }

    @Log("团打卡数据")
    @ApiOperation(value = "团打卡数据")
    @PreAuthorize("hasAnyRole('ADMIN','DRAW_ALL','REPORT_ACT_CARD_LIST')")
    @GetMapping("/findGroupCountBySource")
    public ActResponse<List<Map>> findGroupCountBySource(String source) {
        if (ObjectUtils.isEmpty(source)) {
            return ActResponse.buildParamEmptyError("source");
        }
        List<Map> actGroupVO = actModuleService.findGroupCountBySource(source);
        return ActResponse.buildSuccessResponse(actGroupVO);
    }

    public ActResponse findSignUp(SignUpForm formData){
        return null;

    }
}
