package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Api(tags = "TbWapActModule管理")
@RestController
@RequestMapping("api")
public class ActModuleController {

    @Autowired
    private ActModuleService actModuleService;

    @Log("查询TbWapActModule")
    @ApiOperation(value = "查询TbWapActModule")
    @GetMapping(value = "/actModule")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','TBWAPACTMODULE_SELECT')")
    public ResponseEntity getTbWapActModules(ActModuleQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(actModuleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增TbWapActModule")
    @ApiOperation(value = "新增TbWapActModule")
    @PostMapping(value = "/actModule")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','TBWAPACTMODULE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ActModule resources) {
        return new ResponseEntity(actModuleService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改TbWapActModule")
    @ApiOperation(value = "修改TbWapActModule")
    @PutMapping(value = "/actModule")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','TBWAPACTMODULE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ActModule resources) {
        actModuleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除TbWapActModule")
    @ApiOperation(value = "删除TbWapActModule")
    @DeleteMapping(value = "/actModule/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','TBWAPACTMODULE_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        actModuleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询所有活动信息")
    @ApiOperation(value = "查询所有活动信息")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_LIST')")
    @ResponseBody
    @GetMapping(value = "/actInfo")
    public ActResponse findActInfo() {
        List<ActModule> actInfo = actModuleService.findActInfo();
        return ActResponse.buildSuccessResponse("actsInfo", actInfo);
    }

    @Log("活动信息添加")
    @ApiOperation(value = "活动信息添加")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_CREATE')")
    @ResponseBody
    @PostMapping(value = "/actInfo/add")
    public ActResponse addActInfo(@RequestBody ActModule actInfo) {
        ActResponse actResponse = actModuleService.addActInfo(actInfo);
        return actResponse;
    }

    @Log("活动信息修改")
    @ApiOperation(value = "活动信息修改")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_UPDATE')")
    @ResponseBody
    @PostMapping(value = "/actInfo/save")
    public ActResponse saveActInfo(@RequestBody ActModule actInfo) {
        ActResponse actResponse = actModuleService.saveActInfo(actInfo);
        return actResponse;
    }

    @Log("当前活动优先级改变")
    @ApiOperation(value = "当前活动优先级改变")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_LEVEL_CHANGE')")
    @ResponseBody
    @PostMapping("/actInfo/changeLevel")
    public ActResponse changeActInfoLevel(@RequestParam("actCode") String actCode, @RequestParam("isDown") Boolean isDown) {
        ActResponse actResponse = actModuleService.changActInfoLeveL(isDown, actCode);
        return actResponse;
    }

    @Log("活动逻辑删除-下架")
    @ApiOperation(value = "活动逻辑删除-下架")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_PULL_OFF')")
    @ResponseBody
    @PostMapping("/actInfo/delete")
    public ActResponse deleteAct(@RequestParam("actCode") String actCode) {
        ActResponse actResponse = actModuleService.deleteAct(actCode);
        return actResponse;
    }

    @Log("活动启用-上架")
    @ApiOperation(value = "活动启用-上架")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_PULL_ON')")
    @ResponseBody
    @PostMapping("actInfo/enable")
    public ActResponse enableAct(@RequestParam("actCode") String actCode) {
        ActResponse actResponse = actModuleService.enableAct(actCode);
        return actResponse;
    }

    @Log("活动特殊链接上传")
    @ApiOperation(value = "活动特殊链接上传")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ALL','ACT_SPEC_UPLOAD')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpeclink/upload")
    public ActResponse uploadActSpecLinkInfo(@PathVariable("actCode") String actCode, @RequestParam("specCode") String specCode, @RequestParam("file") MultipartFile file) {
        return actModuleService.uploadActSpecLinkInfo(actCode, specCode, file);
    }

    @Log("活动特殊链接")
    @ApiOperation(value = "活动特殊链接")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ALL','ACT_SPEC_LIST')")
    @ResponseBody
    @GetMapping("/{actCode}/actSpecLink")
    public ActResponse findSpecLink(@PathVariable("actCode") String actCode) {
        ActResponse actResponse = actModuleService.findSpecLink(actCode);
        return actResponse;
    }

    @Log("增加广告位")
    @ApiOperation(value = "增加广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ALL','ACT_SPEC_ADD')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/add")
    public ActResponse addSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        ActResponse actResponse = actModuleService.addSpecLink(actCode, actSpecLink);
        return actResponse;
    }

    @Log("增加广告位")
    @ApiOperation(value = "增加广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ALL','ACT_SPEC_UPDATE')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/save")
    public ActResponse saveSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        return actModuleService.saveSpecLink(actCode, actSpecLink);
    }

    @Log("删除广告位")
    @ApiOperation(value = "删除广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ALL','ACT_SPEC_DELETE')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/delete")
    public ActResponse deleteSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        return actModuleService.deleteSpecLink(actCode, actSpecLink);
    }

    @RequestMapping("findAllActNameAndSource")
    @ResponseBody
    public ActResponse<List> findAllActNameAndSource() {
        List<Map<String, String>> allActNameAndSource = actModuleService.findAllActNameAndSource();
        return ActResponse.buildSuccessResponse(allActNameAndSource);
    }
}