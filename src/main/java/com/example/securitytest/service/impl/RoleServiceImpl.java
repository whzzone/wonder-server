package com.example.securitytest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.RoleMapper;
import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.entity.UserRole;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.MenuService;
import com.example.securitytest.service.RoleMenuService;
import com.example.securitytest.service.RoleService;
import com.example.securitytest.service.UserRoleService;
import com.example.securitytest.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuService menuService;

    @Override
    public PageData<RoleDto> page(RoleQuery query) {
        Page<Role> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(query.getName()))
            queryWrapper.like(Role::getName, query.getName());

        roleMapper.selectPage(page, queryWrapper);

        List<RoleDto> userVos = BeanUtil.copyToList(page.getRecords(), RoleDto.class);

        return new PageData<>(userVos, page.getTotal(), page.getPages());
    }

    @Override
    public boolean isAllExist(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds))
            throw new RuntimeException("roleIds为空");

        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        long count = count(new LambdaQueryWrapper<Role>().in(Role::getId, roleIds));
        return count == roleIds.size();
    }

    @Override
    public List<RoleDto> list(RoleQuery query) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Role::getCode, SecurityUtil.SUPER_ADMIN);

        if (StrUtil.isNotBlank(query.getName()))
            queryWrapper.like(Role::getName, query.getName());

        List<Role> roleList = roleMapper.selectList(queryWrapper);

        return BeanUtil.copyToList(roleList, RoleDto.class);
    }

    @Transactional
    @Override
    public boolean updateById(RoleDto dto) {
        Role entity = getById(dto.getId());
        Assert.notNull(entity, "{} 不存在", dto.getName());

        BeanUtil.copyProperties(dto, entity);
        updateById(entity);

        // 添加角色与权限的关联
        roleMenuService.addRelation(entity.getId(), dto.getMenuIds());

        return true;
    }

    @Override
    public RoleDto beforeUpdateHandler(RoleDto dto) {
        Assert.isFalse(existSameCode(dto.getId(), dto.getCode()), "{} 已存在", dto.getCode());
        Assert.isFalse(existSameName(dto.getId(), dto.getName()), "{} 已存在", dto.getName());
        return dto;
    }

    @Override
    public RoleDto beforeSaveHandler(RoleDto dto) {
        Assert.isFalse(existSameCode(dto.getId(), dto.getCode()), "{} 已存在", dto.getCode());
        Assert.isFalse(existSameName(dto.getId(), dto.getName()), "{} 已存在", dto.getName());
        return dto;
    }

    @Override
    public boolean existSameCode(Long roleId, String code){
        Assert.notEmpty(code, "code 为空");
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getCode, code);
        queryWrapper.ne(roleId != null, Role::getId, roleId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameName(Long roleId, String name){
        Assert.notEmpty(name, "name 为空");
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        queryWrapper.ne(roleId != null, Role::getId, roleId);
        return count(queryWrapper) > 0;
    }

    @Override
    public void addRelation(Long userId, List<Long> roleIds) {
        Assert.notNull(userId);
        Assert.notEmpty(roleIds);

        // 判断角色是否存在
        boolean roleAllExist = isAllExist(roleIds);
        Assert.isTrue(roleAllExist, "角色选择有误");

        // 先删除关联
        removeRelation(userId);

        List<UserRole> entityList = new ArrayList<>();

        // 再添加关联
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            entityList.add(userRole);
        }

        if (CollectionUtil.isNotEmpty(entityList)){
            userRoleService.saveBatch(entityList);
        }
    }

    @Override
    public void removeRelation(Long userId){
        Assert.notNull(userId);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        Assert.notNull(userId);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        queryWrapper.select(UserRole::getRoleId);
        List<UserRole> list = userRoleService.list(queryWrapper);

        return list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public RoleDto afterQueryHandler(RoleDto dto) {
        List<Long> menuIdList = menuService.getIdListByRoleId(dto.getId());
        dto.setMenuIds(menuIdList);
        return dto;
    }
}
