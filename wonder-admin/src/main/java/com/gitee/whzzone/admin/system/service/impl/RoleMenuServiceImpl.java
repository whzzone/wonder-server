package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.RoleMenu;
import com.gitee.whzzone.admin.system.mapper.RoleMenuMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMenuDto;
import com.gitee.whzzone.admin.system.pojo.query.RoleMenuQuery;
import com.gitee.whzzone.admin.system.service.RoleMenuService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:06
 */
@Service
public class RoleMenuServiceImpl extends EntityServiceImpl<RoleMenuMapper, RoleMenu, RoleMenuDto, RoleMenuQuery> implements RoleMenuService {

    @Override
    public void addRelation(Integer roleId, List<Integer> menuIds) {
        // 移除关联
        removeRelation(roleId);

        // 新增关联
        List<RoleMenu> entityList = new ArrayList<>();

        for (Integer menuId : menuIds) {
            RoleMenu entity = new RoleMenu();
            entity.setMenuId(menuId);
            entity.setRoleId(roleId);
            entityList.add(entity);
        }

        super.saveBatch(entityList);
    }

    @Override
    public void removeRelation(Integer roleId) {
        Assert.notNull(roleId, "roleId 为空");
        remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }
}
