package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.system.RoleMarkDto;
import com.gitee.whzzone.pojo.entity.system.RoleMark;
import com.gitee.whzzone.pojo.query.system.RoleMarkQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RoleMarkService extends EntityService<RoleMark, RoleMarkDto, RoleMarkQuery> {

    List<RoleMark> getByRoleId(Long roleId);

    RoleMark getByRoleIdAndMarkId(Long roleId, Long ruleId);

}
