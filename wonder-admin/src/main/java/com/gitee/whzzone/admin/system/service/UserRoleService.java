package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.UserRoleDTO;
import com.gitee.whzzone.admin.system.entity.UserRole;
import com.gitee.whzzone.admin.system.pojo.query.UserRoleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

public interface UserRoleService extends EntityService<UserRole, UserRoleDTO, UserRoleQuery> {

    List<UserRole> getByUserId(Integer userId);

}
