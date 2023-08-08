package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.system.mapper.MenuMapper;
import com.gitee.whzzone.admin.system.pojo.dto.MenuDto;
import com.gitee.whzzone.admin.system.pojo.dto.MenuTreeDto;
import com.gitee.whzzone.admin.system.entity.Menu;
import com.gitee.whzzone.admin.system.entity.RoleMenu;
import com.gitee.whzzone.admin.system.pojo.query.MenuQuery;
import com.gitee.whzzone.admin.system.service.MenuService;
import com.gitee.whzzone.admin.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : whz
 * @date : 2023/5/22 20:17
 */
@Service
public class MenuServiceImpl extends EntityServiceImpl<MenuMapper, Menu, MenuDto, MenuQuery> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> findPermitByUserId(Long userId) {
        return menuMapper.findPermitByUserId(userId);
    }

    @Override
    public List<MenuTreeDto> treeList(MenuQuery query) {
        List<Menu> menuList = getEnabledList();

        List<MenuTreeDto> menuTreeVos = BeanUtil.copyToList(menuList, MenuTreeDto.class);

        //获取父节点
        return menuTreeVos.stream().filter(m -> m.getParentId() == 0).map(
                (m) -> {
                    m.setChildren(getChildrenList(m, menuTreeVos));
                    return m;
                }
        ).collect(Collectors.toList());
    }

    public static List<MenuTreeDto> getChildrenList(MenuTreeDto tree, List<MenuTreeDto> list){
        List<MenuTreeDto> children = list.stream().filter(item -> Objects.equals(item.getParentId(), tree.getId())).map(
                (item) -> {
                    item.setChildren(getChildrenList(item, list));
                    return item;
                }
        ).collect(Collectors.toList());
        return children;
    }

    @Override
    public List<MenuDto> list(MenuQuery query) {
        if (query.getParentId() == null)
            query.setParentId(0L);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, query.getParentId());
        queryWrapper.orderByAsc(Menu::getSort);
        List<Menu> list = list(queryWrapper);
        return afterQueryHandler(list);
    }

    @Override
    public List<Menu> getEnabledList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getEnabled, true);
        queryWrapper.orderByAsc(Menu::getSort);
        return menuMapper.selectList(queryWrapper);
    }

    @Override
    public List<MenuDto> findByUserId(Long userId) {
        List<Menu> byUserId = menuMapper.findByUserId(userId);
        return BeanUtil.copyToList(byUserId, MenuDto.class );
    }

    @Override
    public MenuDto afterQueryHandler(Menu entity) {
        MenuDto dto = super.afterQueryHandler(entity);
        Menu parent = getById(dto.getParentId());
        if (parent != null){
            dto.setParentName(parent.getName());
        }

        long count = count(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, entity.getId()));
        dto.setHasChildren(count > 0);

        return dto;
    }

    @Override
    public List<Long> getIdListByRoleId(Long roleId) {
        if (roleId == null)
            return new ArrayList<>();

        LambdaQueryWrapper<RoleMenu> rmLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rmLambdaQueryWrapper.eq(RoleMenu::getRoleId, roleId);
        rmLambdaQueryWrapper.select(RoleMenu::getMenuId);
        List<RoleMenu> list = roleMenuService.list(rmLambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();

        return list.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 存在相同的权限标识
     * @param id roleId
     * @param permission 权限标识
     */
    @Override
    public boolean existSamePermission(Long id, String permission){
        if (StrUtil.isBlank(permission))
            return false;

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getPermission, permission);
        queryWrapper.ne(id != null, Menu::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameRouteName(Long id, String routeName){
        if (StrUtil.isBlank(routeName))
            return false;

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getRouteName, routeName);
        queryWrapper.ne(id != null, Menu::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public MenuDto beforeUpdateHandler(MenuDto dto) {
        if (existSamePermission(dto.getId(), dto.getPermission()))
            throw new RuntimeException("存在相同的权限标识：" + dto.getPermission());

        if (existSameRouteName(dto.getId(), dto.getRouteName()))
            throw new RuntimeException("存在相同的路由名称：" + dto.getRouteName());

        return dto;
    }

    @Override
    public MenuDto beforeSaveHandler(MenuDto dto) {
        if (existSameRouteName(dto.getId(), dto.getRouteName()))
            throw new RuntimeException("存在相同的路由名称：" + dto.getRouteName());

        if (existSameRouteName(dto.getId(), dto.getRouteName()))
            throw new RuntimeException("存在相同的路由名称：" + dto.getRouteName());

        return dto;
    }

    @Override
    public Menu save(MenuDto dto) {
        if (dto.getParentId() == null){
            dto.setParentId(0L);
        }
        return super.save(dto);
    }

    @Override
    public boolean updateById(MenuDto dto) {
        if (dto.getParentId() == null){
            dto.setParentId(0L);
        }
        return super.updateById(dto);
    }
}
