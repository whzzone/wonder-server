package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.RoleDto;
import com.gitee.whzzone.pojo.entity.Role;
import com.gitee.whzzone.pojo.query.RoleQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 15:38
 */
public interface RoleService extends EntityService<Role, RoleDto> {

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
}
