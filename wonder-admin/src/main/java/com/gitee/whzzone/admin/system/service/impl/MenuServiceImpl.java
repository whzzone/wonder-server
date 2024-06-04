package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.Menu;
import com.gitee.whzzone.admin.system.entity.RoleMenu;
import com.gitee.whzzone.admin.system.mapper.MenuMapper;
import com.gitee.whzzone.admin.system.pojo.dto.MenuDTO;
import com.gitee.whzzone.admin.system.pojo.dto.MenuTreeDTO;
import com.gitee.whzzone.admin.system.pojo.query.MenuQuery;
import com.gitee.whzzone.admin.system.service.MenuService;
import com.gitee.whzzone.admin.system.service.RoleMenuService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
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
public class MenuServiceImpl extends EntityServiceImpl<MenuMapper, Menu, MenuDTO, MenuQuery> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> findPermitByUserId(Integer userId) {
        return menuMapper.findPermitByUserId(userId);
    }

    @Override
    public List<MenuTreeDTO> treeList(MenuQuery query) {
        List<Menu> menuList = getEnabledList();

        List<MenuTreeDTO> menuTreeVos = BeanUtil.copyToList(menuList, MenuTreeDTO.class);

        //获取父节点
        return menuTreeVos.stream().filter(m -> m.getParentId() == 0).map(
                (m) -> {
                    m.setChildren(getChildrenList(m, menuTreeVos));
                    return m;
                }
        ).collect(Collectors.toList());
    }

    public static List<MenuTreeDTO> getChildrenList(MenuTreeDTO tree, List<MenuTreeDTO> list){
        List<MenuTreeDTO> children = list.stream().filter(item -> Objects.equals(item.getParentId(), tree.getId())).map(
                (item) -> {
                    item.setChildren(getChildrenList(item, list));
                    return item;
                }
        ).collect(Collectors.toList());
        return children;
    }

    @Override
    public List<MenuDTO> list(MenuQuery query) {
        if (query.getParentId() == null)
            query.setParentId(0);

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
    public List<MenuDTO> findByUserId(Integer userId) {
        List<Menu> byUserId = menuMapper.findByUserId(userId);
        return BeanUtil.copyToList(byUserId, MenuDTO.class );
    }

    @Override
    public MenuDTO afterQueryHandler(Menu entity) {
        MenuDTO dto = super.afterQueryHandler(entity);
        Menu parent = getById(dto.getParentId());
        if (parent != null){
            dto.setParentName(parent.getName());
        }

        long count = count(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, entity.getId()));
        dto.setHasChildren(count > 0);

        return dto;
    }

    @Override
    public List<Integer> getMenuIdList(Integer roleId) {
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
    public boolean existSamePermission(Integer id, String permission){
        if (StrUtil.isBlank(permission))
            return false;

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getPermission, permission);
        queryWrapper.ne(id != null, Menu::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameRouteName(Integer id, String routeName){
        if (StrUtil.isBlank(routeName))
            return false;

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getRouteName, routeName);
        queryWrapper.ne(id != null, Menu::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public MenuDTO beforeUpdateHandler(MenuDTO dto) {
        if (existSamePermission(dto.getId(), dto.getPermission()))
            throw new RuntimeException("存在相同的权限标识：" + dto.getPermission());

        if (existSameRouteName(dto.getId(), dto.getRouteName()))
            throw new RuntimeException("存在相同的路由名称：" + dto.getRouteName());

        return dto;
    }

    @Override
    public MenuDTO beforeAddHandler(MenuDTO dto) {
        if (existSameRouteName(dto.getId(), dto.getRouteName()))
            throw new RuntimeException("存在相同的路由名称：" + dto.getRouteName());

        return dto;
    }

    @Override
    public Menu add(MenuDTO dto) {
        if (dto.getParentId() == null){
            dto.setParentId(0);
        }
        return super.add(dto);
    }

    @Override
    public MenuDTO beforeAddOrUpdateHandler(MenuDTO dto) {
        if (dto.getParentId() == null){
            dto.setParentId(0);
        }
        return dto;
    }
}
