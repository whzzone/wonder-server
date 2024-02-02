package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.entity.UserRole;
import com.gitee.whzzone.admin.system.mapper.RoleMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import com.gitee.whzzone.admin.system.pojo.query.RoleQuery;
import com.gitee.whzzone.admin.system.service.*;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.annotation.DataScope;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
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
public class RoleServiceImpl extends EntityServiceImpl<RoleMapper, Role, RoleDTO, RoleQuery> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MarkService markService;

    @DataScope("role-page")
    @Override
    public PageData<RoleDTO> page(RoleQuery query) {
        Page<Role> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Role::getName, query.getName());

        page(page, queryWrapper);

        List<RoleDTO> userVos = BeanUtil.copyToList(page.getRecords(), RoleDTO.class);

        return new PageData<>(userVos, page.getTotal(), page.getPages());
    }

    @Override
    public boolean isAllExist(List<Integer> roleIds) {
        if (CollectionUtil.isEmpty(roleIds))
            throw new RuntimeException("roleIds为空");

        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        long count = count(new LambdaQueryWrapper<Role>().in(Role::getId, roleIds));
        return count == roleIds.size();
    }

    @Override
    public List<RoleDTO> list(RoleQuery query) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Role::getCode, SecurityUtil.ADMIN_ROLE_CODE);

        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Role::getName, query.getName());
        return afterQueryHandler(list(queryWrapper));
    }

    @Transactional
    @Override
    public Role updateById(RoleDTO dto) {
        Role entity = getById(dto.getId());
        Assert.notNull(entity, "{} 不存在", dto.getName());

        BeanUtil.copyProperties(dto, entity);
        updateById(entity);

        // 添加角色与权限的关联
        roleMenuService.addRelation(entity.getId(), dto.getMenuIds());

        return entity;
    }

    @Override
    public RoleDTO beforeUpdateHandler(RoleDTO dto) {
        Assert.isFalse(existSameCode(dto.getId(), dto.getCode()), "{} 已存在", dto.getCode());
        Assert.isFalse(existSameName(dto.getId(), dto.getName()), "{} 已存在", dto.getName());
        return dto;
    }

    @Override
    public RoleDTO beforeSaveHandler(RoleDTO dto) {
        Assert.isFalse(existSameCode(dto.getId(), dto.getCode()), "{} 已存在", dto.getCode());
        Assert.isFalse(existSameName(dto.getId(), dto.getName()), "{} 已存在", dto.getName());
        return dto;
    }

    @Override
    public boolean existSameCode(Integer roleId, String code){
        Assert.notEmpty(code, "code 为空");
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getCode, code);
        queryWrapper.ne(roleId != null, Role::getId, roleId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameName(Integer roleId, String name){
        Assert.notEmpty(name, "name 为空");
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        queryWrapper.ne(roleId != null, Role::getId, roleId);
        return count(queryWrapper) > 0;
    }

    @Override
    public void addRelation(Integer userId, List<Integer> roleIds) {
        Assert.notNull(userId);
        Assert.notEmpty(roleIds);

        // 判断角色是否存在
        boolean roleAllExist = isAllExist(roleIds);
        Assert.isTrue(roleAllExist, "角色选择有误");

        // 先删除关联
        removeRelation(userId);

        List<UserRole> entityList = new ArrayList<>();

        // 再添加关联
        for (Integer roleId : roleIds) {
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
    public void removeRelation(Integer userId){
        Assert.notNull(userId);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {
        Assert.notNull(userId);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        queryWrapper.select(UserRole::getRoleId);
        List<UserRole> list = userRoleService.list(queryWrapper);

        return list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public RoleDTO afterQueryHandler(Role entity) {
        RoleDTO dto = super.afterQueryHandler(entity);
        List<Integer> menuIdList = menuService.getIdListByRoleId(dto.getId());
        dto.setMenuIds(menuIdList);
        return dto;
    }

    @Override
    public Role save(RoleDTO dto) {
        Role save = super.save(dto);
        // 添加角色与权限的关联
        roleMenuService.addRelation(save.getId(), dto.getMenuIds());
        return save;
    }

    @Override
    public void enabledSwitch(Integer id) {
        Role entity = getById(id);
        if (entity == null){
            throw new RuntimeException("角色不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Override
    public List<RoleDTO> getDTOListIn(List<Integer> ids) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BaseEntity::getId, ids);
        List<Role> list = list(queryWrapper);
        return BeanUtil.copyToList(list, RoleDTO.class);
    }

    @Transactional
    @Override
    public void bindingRule(Integer roleId, Integer ruleId) {
        if (!isExist(roleId))
            throw new RuntimeException("不存在角色：" + roleId);

        Rule rule = ruleService.getById(ruleId);
        if (rule == null)
            throw new RuntimeException("不存在规则：" + ruleId);

        Integer markId = rule.getMarkId();

        markService.removeAllByRoleIdAndMarkId(roleId, markId);

        if (!markService.addRelation(roleId, markId, ruleId)) {
            throw new RuntimeException("角色关联规则失败");
        }
    }

    @Override
    public void unBindingRule(Integer roleId, Integer ruleId) {
        if (!isExist(roleId))
            throw new RuntimeException("不存在角色：" + roleId);

        Rule rule = ruleService.getById(ruleId);
        if (rule == null)
            throw new RuntimeException("不存在规则：" + ruleId);

        Integer markId = rule.getMarkId();
        markService.removeAllByRoleIdAndMarkId(roleId, markId);
    }

    @Override
    public List<String> getRoleCodesByUserId(Integer userId) {
        Assert.notNull(userId);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        queryWrapper.select(UserRole::getRoleId);
        List<UserRole> list = userRoleService.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        List<Integer> roleIds = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.select(Role::getCode);
        roleLambdaQueryWrapper.in(BaseEntity::getId, roleIds);
        List<Role> roleList = this.list(roleLambdaQueryWrapper);

        if (CollectionUtil.isEmpty(roleList)) {
            return new ArrayList<>();
        }

        return roleList.stream().map(Role::getCode).collect(Collectors.toList());
    }
}
