package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.system.UserRoleDto;
import com.gitee.whzzone.pojo.entity.system.UserRole;
import com.gitee.whzzone.pojo.query.system.UserRoleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

public interface UserRoleService extends EntityService<UserRole, UserRoleDto, UserRoleQuery> {

    List<UserRole> getByUserId(Long userId);

}
