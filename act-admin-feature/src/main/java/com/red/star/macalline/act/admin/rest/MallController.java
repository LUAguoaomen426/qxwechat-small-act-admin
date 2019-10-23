package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.service.ComService;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.service.MallService;
import com.red.star.macalline.act.admin.service.dto.MallQueryCriteria;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Api(tags = "TbWapMall管理")
@RestController
@RequestMapping("api")
public class MallController {

    @Autowired
    private MallService mallService;

    @Resource
    private ComService comService;

    @Log("查询TbWapMall")
    @ApiOperation(value = "查询TbWapMall")
    @GetMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPMALL_ALL','TBWAPMALL_SELECT')")
    public ResponseEntity getTbWapMalls(MallQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(mallService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增TbWapMall")
    @ApiOperation(value = "新增TbWapMall")
    @PostMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPMALL_ALL','TBWAPMALL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Mall resources) {
        return new ResponseEntity(mallService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改TbWapMall")
    @ApiOperation(value = "修改TbWapMall")
    @PutMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPMALL_ALL','TBWAPMALL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody List<Mall> malls) {
        mallService.update(malls);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除TbWapMall")
    @ApiOperation(value = "删除TbWapMall")
    @DeleteMapping(value = "/mall/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TBWAPMALL_ALL','TBWAPMALL_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        mallService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 基础商场数据同步
     *
     * @return
     */
    @Log("同步TbWapMall")
    @ApiOperation(value = "同步TbWapMall")
    @GetMapping("/mallBaseInfo/async")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_SYNC')")
    public ResponseEntity SyncMallInfo() {
        mallService.syncMallInfo();
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 上传excel批量修改某活动下的商场是否参与
     *
     * @param actCode
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/{actCode}/mallInfo/upload")
    public ActResponse uploadMallInfo(@PathVariable("actCode") String actCode, @RequestParam("file") MultipartFile file) {
        return mallService.uploadMallinfo(actCode, file);
    }





    /**
     * 查询某次活动下的商场信息
     *
     * @param actCode
     * @return
     */
    @Log("查询某次活动下的商场信息")
    @ApiOperation(value = "查询某次活动下的商场信息")
    @GetMapping(value = "/{actCode}/mallInfo")
    public ActResponse findMallByActCode(@PathVariable("actCode") String actCode) {
        ActResponse res = mallService.findMallByActCode(actCode);
        return res;
    }

    /**
     * 某次活动商场数据保存
     *
     * @param actCode
     * @param mallList
     * @return
     */
    @ResponseBody
    @PostMapping("/{actCode}/mallInfo/save")
    public ActResponse saveMallInfo(@PathVariable("actCode") String actCode, @RequestBody List<Mall> mallList) {
        ActResponse actResponse = mallService.saveMallInfo(actCode, mallList);
        return actResponse;
    }


    /**
     * 更改默认商场
     *
     * @param omsCode
     * @return
     */
    @ResponseBody
    @PostMapping("/mallBaseInfo/changeDefult")
    public ActResponse changeDefultMall(@RequestParam("omsCode") String omsCode) {
        ActResponse actResponse = mallService.changeDefultMall(omsCode);
        return actResponse;
    }


    /**
     * 刷新商场配置
     *
     * @param actCode
     * @param omsCode
     * @return
     */
    @ResponseBody
    @PostMapping("/{actCode}/mallInfo/refresh")
    public ActResponse reflushMall(@PathVariable("actCode") String actCode, @RequestParam("omsCode") String omsCode) {
        comService.preheatMallExtendInfo(actCode, omsCode);
        return ActResponse.buildSuccessResponse();
    }

}