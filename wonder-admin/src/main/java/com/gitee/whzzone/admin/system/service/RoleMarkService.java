package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMarkDto;
import com.gitee.whzzone.admin.system.entity.RoleMark;
import com.gitee.whzzone.admin.system.pojo.query.RoleMarkQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RoleMarkService extends EntityService<RoleMark, RoleMarkDto, RoleMarkQuery> {

    List<RoleMark> getByRoleId(Integer roleId);

    List<RoleMark> getByRoleIdAndMarkId(Integer roleId, Integer ruleId);

}
