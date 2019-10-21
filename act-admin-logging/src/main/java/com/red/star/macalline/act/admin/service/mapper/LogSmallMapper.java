package com.red.star.macalline.act.admin.service.mapper;

import com.red.star.macalline.act.admin.service.dto.LogSmallDTO;
import com.red.star.macalline.act.admin.domain.Log;
import com.red.star.macalline.act.admin.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends EntityMapper<LogSmallDTO, Log> {

}