package com.red.star.macalline.act.admin.service.mapper;

import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.mapper.EntityMapper;
import com.red.star.macalline.act.admin.service.dto.MallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MallMapper extends EntityMapper<MallDTO, Mall> {

}