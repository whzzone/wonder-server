package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.MenuTreeDto;
import com.gitee.whzzone.admin.system.pojo.dto.MenuDto;
import com.gitee.whzzone.admin.system.entity.Menu;
import com.gitee.whzzone.admin.system.pojo.query.MenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 20:17
 */
public interface MenuService  extends EntityService<Menu, MenuDto, MenuQuery> {
    List<String> findPermitByUserId(Long userId);

    List<MenuTreeDto> treeList(MenuQuery query);

    List<MenuDto> list(MenuQuery query);

    List<Menu> getEnabledList();

    List<MenuDto> findByUserId(Long userId);

    List<Long> getIdListByRoleId(Long id);

    boolean existSamePermission(Long id, String Permission);

    boolean existSameRouteName(Long id, String routeName);
}
