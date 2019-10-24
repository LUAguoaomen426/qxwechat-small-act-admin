package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.Mall;
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

import java.util.List;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Api(tags = "商场管理")
@RestController
@RequestMapping("api")
public class MallController {

    @Autowired
    private MallService mallService;

    @Log("查询TbWapMall")
    @ApiOperation(value = "查询TbWapMall")
    @GetMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_LIST')")
    public ResponseEntity getTbWapMalls(MallQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(mallService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增TbWapMall")
    @ApiOperation(value = "新增TbWapMall")
    @PostMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Mall resources) {
        return new ResponseEntity(mallService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改TbWapMall")
    @ApiOperation(value = "修改TbWapMall")
    @PutMapping(value = "/mall")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody List<Mall> malls) {
        mallService.update(malls);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除TbWapMall")
    @ApiOperation(value = "删除TbWapMall")
    @DeleteMapping(value = "/mall/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        mallService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("同步TbWapMall")
    @ApiOperation(value = "同步TbWapMall")
    @GetMapping("/mallBaseInfo/async")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_SYNC')")
    public ResponseEntity syncMallInfo() {
        mallService.syncMallInfo();
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("更改默认商场")
    @ApiOperation(value = "更改默认商场")
    @PostMapping("/mallBaseInfo/changeDefult")
    @PreAuthorize("hasAnyRole('ADMIN','MALL_ALL','MALL_CHANGE_DEFAULT')")
    public ActResponse changeDefultMall(@RequestParam("omsCode") String omsCode) {
        ActResponse actResponse = mallService.changeDefultMall(omsCode);
        return actResponse;
    }
}