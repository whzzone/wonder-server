package com.gitee.whzzone.admin.service.system.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.RoleMenuMapper;
import com.gitee.whzzone.admin.pojo.dto.system.RoleMenuDto;
import com.gitee.whzzone.admin.pojo.entity.system.RoleMenu;
import com.gitee.whzzone.admin.pojo.query.system.RoleMenuQuery;
import com.gitee.whzzone.admin.service.system.RoleMenuService;
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

        super.saveBatch(entityList);
    }

    @Override
    public void removeRelation(Long roleId) {
        Assert.notNull(roleId, "roleId 为空");
        remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }
}
