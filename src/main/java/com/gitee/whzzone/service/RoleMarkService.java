package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.RoleMarkDto;
import com.gitee.whzzone.pojo.entity.RoleMark;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RoleMarkService extends EntityService<RoleMark, RoleMarkDto> {

    List<RoleMark> getByRoleId(Long roleId);

    RoleMark getByRoleIdAndMarkId(Long roleId, Long ruleId);

}
