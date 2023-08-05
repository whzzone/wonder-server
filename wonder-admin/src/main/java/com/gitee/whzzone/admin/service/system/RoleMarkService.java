package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.RoleMarkDto;
import com.gitee.whzzone.admin.pojo.entity.system.RoleMark;
import com.gitee.whzzone.admin.pojo.query.system.RoleMarkQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RoleMarkService extends EntityService<RoleMark, RoleMarkDto, RoleMarkQuery> {

    List<RoleMark> getByRoleId(Long roleId);

    RoleMark getByRoleIdAndMarkId(Long roleId, Long ruleId);

}
