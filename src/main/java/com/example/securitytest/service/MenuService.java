package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.MenuTreeDto;
import com.example.securitytest.pojo.dto.MenuDto;
import com.example.securitytest.pojo.entity.Menu;
import com.example.securitytest.pojo.query.MenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 20:17
 */
public interface MenuService  extends IEntityService<Menu, MenuDto>{
    List<String> findPermitByUserId(Long userId);

    List<MenuTreeDto> treeList(MenuQuery query);

    List<MenuTreeDto> list(MenuQuery query);

    List<Menu> getEnabledList();

    List<MenuDto> findByUserId(Long userId);

    List<Long> getIdListByRoleId(Long id);
}
