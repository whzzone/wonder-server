package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.PageData;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 15:38
 */
public interface RoleService extends IEntityService<Role, RoleDto> {

    PageData<RoleDto> page(RoleQuery query);

    boolean isAllExist(List<Long> roleIds);

    List<RoleDto> list(RoleQuery query);

    boolean existSameCode(Long roleId, String code);

    boolean existSameName(Long roleId, String name);

    void addRelation(Long userId, List<Long> roleIds);

    void removeRelation(Long userId);

    List<Long> getRoleIdsByUserId(Long userId);

    void enabledSwitch(Long id);
}
