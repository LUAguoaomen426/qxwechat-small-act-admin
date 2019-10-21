package com.red.star.macalline.act.admin.modules.system.service.mapper;

import com.red.star.macalline.act.admin.modules.system.domain.Menu;
import com.red.star.macalline.act.admin.mapper.EntityMapper;
import com.red.star.macalline.act.admin.modules.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
