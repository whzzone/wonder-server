package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDto;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.pojo.query.RoleQuery;

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
