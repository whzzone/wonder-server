package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.UserRoleDto;
import com.gitee.whzzone.admin.pojo.entity.system.UserRole;
import com.gitee.whzzone.admin.pojo.query.system.UserRoleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

public interface UserRoleService extends EntityService<UserRole, UserRoleDto, UserRoleQuery> {

    List<UserRole> getByUserId(Long userId);

}
