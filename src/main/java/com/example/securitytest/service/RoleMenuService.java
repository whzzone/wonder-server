package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.RoleMenuDto;
import com.example.securitytest.pojo.entity.RoleMenu;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends IEntityService<RoleMenu, RoleMenuDto> {

    void insertBatch(List<RoleMenu> roleMenuList);

    void addRelation(Long roleId, List<Long> menuIds);

    void removeRelation(Long roleId);

}
