package com.example.securitytest.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.RoleMenuMapper;
import com.example.securitytest.pojo.entity.RoleMenu;
import com.example.securitytest.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:06
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void insertBatch(List<RoleMenu> roleMenuList) {
        if (CollectionUtil.isNotEmpty(roleMenuList))
            roleMenuMapper.insertBatch(roleMenuList);
    }

    @Override
    public void addRelation(Long roleId, List<Long> menuIds) {
        // 移除关联
        removeRelation(roleId);

        // 新增关联
        List<RoleMenu> entityList = new ArrayList<>();

        for (Long menuId : menuIds) {
            RoleMenu entity = new RoleMenu();
            entity.setMenuId(menuId);
            entity.setRoleId(roleId);
            entityList.add(entity);
        }

        insertBatch(entityList);
    }

    @Override
    public void removeRelation(Long roleId) {
        Assert.notNull(roleId, "roleId 为空");
        remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }
}
