package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.UserRoleDto;
import com.gitee.whzzone.admin.system.entity.UserRole;
import com.gitee.whzzone.admin.system.pojo.query.UserRoleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

public interface UserRoleService extends EntityService<UserRole, UserRoleDto, UserRoleQuery> {

    List<UserRole> getByUserId(Long userId);

}
