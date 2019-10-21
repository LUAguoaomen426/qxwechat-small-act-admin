package com.red.star.macalline.act.admin.service.mapper;

import com.red.star.macalline.act.admin.mapper.EntityMapper;
import com.red.star.macalline.act.admin.domain.LocalStorage;
import com.red.star.macalline.act.admin.service.dto.LocalStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends EntityMapper<LocalStorageDTO, LocalStorage> {

}