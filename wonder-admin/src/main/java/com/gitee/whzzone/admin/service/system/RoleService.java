package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.PageData;
import com.gitee.whzzone.admin.pojo.dto.system.RoleDto;
import com.gitee.whzzone.admin.pojo.entity.system.Role;
import com.gitee.whzzone.admin.pojo.query.system.RoleQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 15:38
 */
public interface RoleService extends EntityService<Role, RoleDto, RoleQuery> {

    PageData<RoleDto> page(RoleQuery query);

    boolean isAllExist(List<Long> roleIds);

    List<RoleDto> list(RoleQuery query);

    boolean existSameCode(Long roleId, String code);

    boolean existSameName(Long roleId, String name);

    void addRelation(Long userId, List<Long> roleIds);

    void removeRelation(Long userId);

    List<Long> getRoleIdsByUserId(Long userId);

    void enabledSwitch(Long id);

    List<RoleDto> getDtoListIn(List<Long> roleIds);

    void bindingRule(Long roleId, Long ruleId);

    void unBindingRule(Long roleId, Long ruleId);
}
