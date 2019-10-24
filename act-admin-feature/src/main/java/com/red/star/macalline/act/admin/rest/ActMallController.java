package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.service.ComService;
import com.red.star.macalline.act.admin.service.MallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.rest
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-24 17:38
 * @Version: 1.0
 */
@Api(tags = "活动商场管理")
@RestController
@RequestMapping("api")
public class ActMallController {

    @Autowired
    private MallService mallService;

    @Resource
    private ComService comService;

    @Log("上传excel同步上传")
    @ApiOperation(value = "上传excel同步上传")
    @PostMapping("/{actCode}/mallInfo/upload")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_MALL_ALL','ACT_MALL_EXCEL_SYNC')")
    public ActResponse uploadMallInfo(@PathVariable("actCode") String actCode, @RequestParam("file") MultipartFile file) {
        return mallService.uploadMallinfo(actCode, file);
    }

    @Log("查询某次活动下的商场信息")
    @ApiOperation(value = "查询某次活动下的商场信息")
    @GetMapping(value = "/{actCode}/mallInfo")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_MALL_ALL','ACT_MALL_SELECT_ACT')")
    public ActResponse findMallByActCode(@PathVariable("actCode") String actCode) {
        ActResponse res = mallService.findMallByActCode(actCode);
        return res;
    }

    @Log("某次活动商场数据保存")
    @ApiOperation(value = "某次活动商场数据保存")
    @PostMapping("/{actCode}/mallInfo/save")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_MALL_ALL','ACT_MALL_SAVE_ACT')")
    public ActResponse saveMallInfo(@PathVariable("actCode") String actCode, @RequestBody List<Mall> mallList) {
        ActResponse actResponse = mallService.saveMallInfo(actCode, mallList);
        return actResponse;
    }

    @Log("刷新商场配置")
    @ApiOperation(value = "刷新商场配置")
    @PostMapping("/{actCode}/mallInfo/refresh")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_MALL_ALL','ACT_MALL_REFRESH_ACT')")
    public ActResponse reflushMall(@PathVariable("actCode") String actCode, @RequestParam("omsCode") String omsCode) {
        comService.preheatMallExtendInfo(actCode, omsCode);
        return ActResponse.buildSuccessResponse();
    }
}
