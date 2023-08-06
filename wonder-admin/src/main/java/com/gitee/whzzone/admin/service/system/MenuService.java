package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.MenuTreeDto;
import com.gitee.whzzone.admin.pojo.dto.system.MenuDto;
import com.gitee.whzzone.admin.pojo.entity.system.Menu;
import com.gitee.whzzone.admin.pojo.query.system.MenuQuery;

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
