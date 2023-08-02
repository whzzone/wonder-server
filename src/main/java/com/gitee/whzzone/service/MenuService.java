package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.MenuTreeDto;
import com.gitee.whzzone.pojo.dto.MenuDto;
import com.gitee.whzzone.pojo.entity.Menu;
import com.gitee.whzzone.pojo.query.MenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 20:17
 */
public interface MenuService  extends EntityService<Menu, MenuDto> {
    List<String> findPermitByUserId(Long userId);

    List<MenuTreeDto> treeList(MenuQuery query);

    List<MenuTreeDto> list(MenuQuery query);

    List<Menu> getEnabledList();

    List<MenuDto> findByUserId(Long userId);

    List<Long> getIdListByRoleId(Long id);

    boolean existSamePermission(Long id, String Permission);

    boolean existSameRouteName(Long id, String routeName);
}
