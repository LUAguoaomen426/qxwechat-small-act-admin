package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.ActExtraNumber;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.SourcePvUvVo;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPACTMODULE_ALL','TBWAPACTMODULE_SELECT')")
    public ResponseEntity getTbWapActModules(ActModuleQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(actModuleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增TbWapActModule")
    @ApiOperation(value = "新增TbWapActModule")
    @PostMapping(value = "/actModule")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPACTMODULE_ALL','TBWAPACTMODULE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ActModule resources) {
        return new ResponseEntity(actModuleService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改TbWapActModule")
    @ApiOperation(value = "修改TbWapActModule")
    @PutMapping(value = "/actModule")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPACTMODULE_ALL','TBWAPACTMODULE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ActModule resources) {
        actModuleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除TbWapActModule")
    @ApiOperation(value = "删除TbWapActModule")
    @DeleteMapping(value = "/actModule/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPACTMODULE_ALL','TBWAPACTMODULE_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        actModuleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 查询所有活动信息
     *
     * @return
     */
    @Log("查询所有活动信息")
    @ApiOperation(value = "查询所有活动信息")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_LIST')")
    @ResponseBody
    @GetMapping(value = "/actInfo")
    public ActResponse findActInfo() {
        List<ActModule> actInfo = actModuleService.findActInfo();
        return ActResponse.buildSuccessResponse("actsInfo", actInfo);
    }

    /**
     * 活动信息添加
     *
     * @param actInfo
     * @return
     */
    @Log("活动信息添加")
    @ApiOperation(value = "活动信息添加")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_CREATE')")
    @ResponseBody
    @PostMapping(value = "/actInfo/add")
    public ActResponse addActInfo(@RequestBody ActModule actInfo) {
        ActResponse actResponse = actModuleService.addActInfo(actInfo);
        return actResponse;
    }

    /**
     * 活动信息修改
     *
     * @param actInfo
     * @return
     */
    @Log("活动信息修改")
    @ApiOperation(value = "活动信息修改")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_UPDATE')")
    @ResponseBody
    @PostMapping(value = "/actInfo/save")
    public ActResponse saveActInfo(@RequestBody ActModule actInfo) {
        ActResponse actResponse = actModuleService.saveActInfo(actInfo);
        return actResponse;
    }


    /**
     * 当前活动优先级改变
     *
     * @param actCode
     * @param isDown
     * @return
     */
    @Log("当前活动优先级改变")
    @ApiOperation(value = "当前活动优先级改变")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_DOWN')")
    @ResponseBody
    @PostMapping("/actInfo/changeLevel")
    public ActResponse changeActInfoLevel(@RequestParam("actCode") String actCode, @RequestParam("isDown") Boolean isDown) {
        ActResponse actResponse = actModuleService.changActInfoLeveL(isDown, actCode);
        return actResponse;
    }

    /**
     * 活动逻辑删除-下架
     *
     * @param actCode
     * @return
     */
    @Log("活动逻辑删除-下架")
    @ApiOperation(value = "活动逻辑删除-下架")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_PULL_OFF')")
    @ResponseBody
    @PostMapping("/actInfo/delete")
    public ActResponse deleteAct(@RequestParam("actCode") String actCode) {
        ActResponse actResponse = actModuleService.deleteAct(actCode);
        return actResponse;
    }


    /**
     * 活动启用-上架
     *
     * @param actCode
     * @return
     */
    @Log("活动启用-上架")
    @ApiOperation(value = "活动启用-上架")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_PULL_ON')")
    @ResponseBody
    @PostMapping("actInfo/enable")
    public ActResponse enableAct(@RequestParam("actCode") String actCode) {
        ActResponse actResponse = actModuleService.enableAct(actCode);
        return actResponse;
    }


    /**
     * 根据上传文件批量更新对应的活动特殊链接某商场是否启用
     *
     * @param actCode
     * @param specCode
     * @param file
     * @return
     */
    @Log("活动特殊链接上传")
    @ApiOperation(value = "活动特殊链接上传")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_UPLOAD')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpeclink/upload")
    public ActResponse uploadActSpecLinkInfo(@PathVariable("actCode") String actCode, @RequestParam("specCode") String specCode, @RequestParam("file") MultipartFile file) {
        return actModuleService.uploadActSpecLinkInfo(actCode, specCode, file);
    }

    /**
     * 查询某次活动特殊链接
     *
     * @param actCode
     * @return
     */
    @Log("活动特殊链接")
    @ApiOperation(value = "活动特殊链接")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_LIST')")
    @ResponseBody
    @GetMapping("/{actCode}/actSpecLink")
    public ActResponse findSpecLink(@PathVariable("actCode") String actCode) {
        ActResponse actResponse = actModuleService.findSpecLink(actCode);
        return actResponse;
    }

    /**
     * 某次活动特殊链接新增
     *
     * @param actCode
     * @param actSpecLink
     */
    @Log("增加广告位")
    @ApiOperation(value = "增加广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_ADD')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/add")
    public ActResponse addSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        ActResponse actResponse = actModuleService.addSpecLink(actCode, actSpecLink);
        return actResponse;
    }

    /**
     * 特殊链接保存
     *
     * @param actCode
     * @param actSpecLink
     */
    @Log("增加广告位")
    @ApiOperation(value = "增加广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_UPDATE')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/save")
    public ActResponse saveSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        return actModuleService.saveSpecLink(actCode, actSpecLink);
    }

    /**
     * 特殊链接的删除
     *
     * @param actCode
     * @param actSpecLink
     * @return
     */
    @Log("删除广告位")
    @ApiOperation(value = "删除广告位")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_SPEC_DELETE')")
    @ResponseBody
    @PostMapping("/{actCode}/actSpecLink/delete")
    public ActResponse deleteSpecLink(@PathVariable("actCode") String actCode, @RequestBody ActSpecLink actSpecLink) {
        return actModuleService.deleteSpecLink(actCode, actSpecLink);
    }

    /**
     * 参团/单品券人数管理
     *
     * @return
     */
    @Log("参团/单品券人数管理")
    @ApiOperation(value = "参团/单品券人数管理")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_GROUP_LIST')")
    @RequestMapping("/number")
    public ActResponse number(String source) {
        return actModuleService.number(source);
    }

    /**
     * 添加额外人数 ACT__GROUP_ADD
     *
     * @return
     */
    @Log("添加额外人数")
    @ApiOperation(value = "添加额外人数")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_GROUP_ADD')")
    @ResponseBody
    @PostMapping("/addGroupNumber")
    public ActResponse addGroupNumber(String source, Integer addGroupNumber) {
        actModuleService.addGroupNumber(source, addGroupNumber);
        return ActResponse.buildSuccessResponse();
    }

    /**
     * 添加额外人数
     *
     * @return
     */
    @Log("添加单品券额外人数")
    @ApiOperation(value = "添加单品券额外人数")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_TICKET_ADD')")
    @ResponseBody
    @PostMapping("/addTicketNumber")
    public ActResponse addTicketNumber(String source, ActExtraNumber actExtraNumber) throws IOException {
        actModuleService.changeTicketNumber(source, actExtraNumber);
        return ActResponse.buildSuccessResponse();
    }

    /**
     * 获取每个团的打卡次数
     *
     * @param source
     * @return
     */
    @Log("团打卡数据")
    @ApiOperation(value = "团打卡数据")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT_CARD_LIST')")
    @RequestMapping("/findGroupCountBySource")
    @ResponseBody
    public ActResponse<List<Map>> findGroupCountBySource(String source) {
        if (ObjectUtils.isEmpty(source)) {
            return ActResponse.buildParamEmptyError("source");
        }
        List<Map> actGroupVO = actModuleService.findGroupCountBySource(source);
        return ActResponse.buildSuccessResponse(actGroupVO);
    }


    @RequestMapping("findAllActNameAndSource")
    @ResponseBody
    public ActResponse<List> findAllActNameAndSource() {
        List<Map<String, String>> allActNameAndSource = actModuleService.findAllActNameAndSource();
        return ActResponse.buildSuccessResponse(allActNameAndSource);
    }

    /**
     * 活动pv、uv
     *
     * @param sourcePvUvBo
     * @return
     */
    @Log("活动pv、uv")
    @ApiOperation(value = "活动pv、uv")
    @PreAuthorize("hasAnyRole('ADMIN','ACT_ALL','ACT__DATA_PV')")
    @ResponseBody
    @PostMapping(value = "/analysisPVUVData")
    public ActResponse<SourcePvUvVo> analysisPVUVData(@RequestBody @Valid SourcePvUvBo sourcePvUvBo) {
        List<SourcePvUvVo> list = actModuleService.analysisPVUVData(sourcePvUvBo);
        return ActResponse.buildSuccessResponse(list);
    }

}