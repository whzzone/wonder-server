package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.RoleMenuMapper;
import com.example.securitytest.pojo.dto.RoleMenuDto;
import com.example.securitytest.pojo.entity.BaseEntity;
import com.example.securitytest.pojo.entity.RoleMenu;
import com.example.securitytest.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteByRoleId(Long roleId) {
        LambdaUpdateWrapper<RoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BaseEntity::getDeleted, true);
        updateWrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuMapper.update(null, updateWrapper);
    }

    @Override
    public void insertBatch(List<RoleMenu> roleMenuList) {
        roleMenuMapper.insertBatch(roleMenuList);
    }

    @Override
    public Class<RoleMenuDto> getDtoClass() {
        return RoleMenuDto.class;
    }
}
