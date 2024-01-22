package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import com.gitee.whzzone.admin.system.pojo.query.RoleQuery;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.EntityService;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 15:38
 */
public interface RoleService extends EntityService<Role, RoleDTO, RoleQuery> {

    PageData<RoleDTO> page(RoleQuery query);

    boolean isAllExist(List<Integer> roleIds);

    List<RoleDTO> list(RoleQuery query);

    boolean existSameCode(Integer roleId, String code);

    boolean existSameName(Integer roleId, String name);

    void addRelation(Integer userId, List<Integer> roleIds);

    void removeRelation(Integer userId);

    List<Integer> getRoleIdsByUserId(Integer userId);

    void enabledSwitch(Integer id);

    List<RoleDTO> getDTOListIn(List<Integer> roleIds);

    void bindingRule(Integer roleId, Integer ruleId);

    void unBindingRule(Integer roleId, Integer ruleId);
}
