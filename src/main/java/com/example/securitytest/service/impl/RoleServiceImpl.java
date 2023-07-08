package com.example.securitytest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.RoleMapper;
import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.entity.RoleMenu;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.IdAndNameVo;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.RoleMenuService;
import com.example.securitytest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : whz
 * @date : 2023/5/22 15:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<IdAndNameVo> getAll() {
        List<Role> roles = roleMapper.selectList(null);

        List<IdAndNameVo> voList = new ArrayList<>();

        for (Role role : roles) {
            voList.add(new IdAndNameVo(role.getId(), role.getName()));
        }

        return voList;
    }

    @Override
    public PageData<RoleDto> findPage(RoleQuery query) {
        Page<Role> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(query.getName()))
            queryWrapper.like(Role::getName, query.getName());

        roleMapper.selectPage(page, queryWrapper);

        List<RoleDto> userVos = BeanUtil.copyToList(page.getRecords(), RoleDto.class);

        return new PageData<>(userVos, page.getTotal(), page.getPages());
    }

    @Override
    @Transactional
    public void updateRoleMenu(RoleDto dto) {
        Long roleId = dto.getId();
        if (Objects.isNull(roleId)){
            throw new RuntimeException("角色id不能为空");
        }

        List<RoleMenu> roleMenuList = new ArrayList<>();

        List<Long> menuIds = dto.getMenuIds();
        for (Long menuId : menuIds) {
            RoleMenu sysRoleMenu = new RoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);
            roleMenuList.add(sysRoleMenu);
        }

        roleMenuService.deleteByRoleId(roleId);

//        roleMenuService.insertBatch(roleMenuList);
        roleMenuService.saveBatch(roleMenuList);
    }
}
