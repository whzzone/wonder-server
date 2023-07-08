package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.RoleMenuDto;
import com.example.securitytest.pojo.entity.RoleMenu;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends IEntityService<RoleMenu, RoleMenuDto> {
    void deleteByRoleId(Long roleId);

    void insertBatch(List<RoleMenu> roleMenuList);
}
