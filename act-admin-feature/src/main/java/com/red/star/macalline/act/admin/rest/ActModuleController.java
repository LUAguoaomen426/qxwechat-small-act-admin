package com.red.star.macalline.act.admin.rest;

import com.red.star.macalline.act.admin.aop.log.Log;
import com.red.star.macalline.act.admin.domain.ActModule;
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


}