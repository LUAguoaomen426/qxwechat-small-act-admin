package com.red.star.macalline.act.admin.modules.system.service.mapper;

import com.red.star.macalline.act.admin.modules.system.domain.Role;
import com.red.star.macalline.act.admin.mapper.EntityMapper;
import com.red.star.macalline.act.admin.modules.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class, MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
