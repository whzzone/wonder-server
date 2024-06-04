package com.gitee.whzzone.admin.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.whzzone.admin.business.pojo.vo.IdNameVo;
import com.gitee.whzzone.admin.common.exception.LimitLoginException;
import com.gitee.whzzone.admin.common.exception.UserNotFoundException;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.*;
import com.gitee.whzzone.admin.system.mapper.UserMapper;
import com.gitee.whzzone.admin.system.pojo.dto.ChangePasswdDTO;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPasswdDTO;
import com.gitee.whzzone.admin.system.pojo.dto.UserDTO;
import com.gitee.whzzone.admin.system.pojo.query.UserQuery;
import com.gitee.whzzone.admin.system.service.*;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author :whz
 * @date : 2023/5/16 23:07
 */
@Slf4j
@Service
public class UserServiceImpl extends EntityServiceImpl<UserMapper, User, UserDTO, UserQuery> implements UserService {

    @Autowired
    private UserDeptService userDeptService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private WonderProperties wonderProperties;

    @Override
    public void loginCheck(User user) {
        // 判断个人情况
        if (Objects.isNull(user))
            throw new UserNotFoundException("该用户不存在");

        if (!user.getEnabled())
            throw new LimitLoginException("该用户已被禁止登录");

        Integer userId = user.getId();

        List<Dept> depts = getDepts(userId);
        if (CollectionUtil.isNotEmpty(depts)) {
            long count = depts.stream().filter(Dept::getEnabled).count();
            if (count == 0) {
                throw new LimitLoginException("该用户所在部门已被禁止登录");
            }
        }

        List<Role> roles = getRoles(userId);
        if (CollectionUtil.isNotEmpty(depts)) {
            long count = roles.stream().filter(Role::getEnabled).count();
            if (count == 0) {
                throw new LimitLoginException("该用户所属角色已被禁止登录");
            }
        }
    }


    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public User getByOpenid(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenId, openid);
        return getOne(queryWrapper);
    }

    @Override
    public UserDTO afterQueryHandler(User entity) {
        UserDTO dto = super.afterQueryHandler(entity);
        dto.setPassword("");

        // 处理部门
        List<Dept> deptList = getUserDeptInfo(dto.getId());
        if (CollectionUtil.isNotEmpty(deptList)) {
            List<IdNameVo> deptVos = new ArrayList<>();
            for (Dept dept : deptList) {
                deptVos.add(new IdNameVo(dept.getId(), dept.getName()));
            }
            dto.setDeptList(deptVos);
            List<Integer> ids = deptList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            dto.setDeptIdList(ids);
        }

        // 处理角色
        List<Role> roleList = getUserRoleInfo(dto.getId());
        if (CollectionUtil.isNotEmpty(roleList)) {
            List<IdNameVo> roleVos = new ArrayList<>();
            for (Role role : roleList) {
                roleVos.add(new IdNameVo(role.getId(), role.getName()));
            }
            dto.setRoleList(roleVos);
            List<Integer> ids = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            dto.setRoleIdList(ids);
        }

        return dto;
    }

    private List<Role> getUserRoleInfo(Integer userId) {
        List<UserRole> userRoleList = userRoleService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userRoleList))
            return new ArrayList<>();

        List<Integer> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        return roleService.listByIds(roleIds);
    }

    @Override
    public List<Dept> getUserDeptInfo(Integer userId) {
        List<UserDept> userDeptList = userDeptService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userDeptList))
            return new ArrayList<>();

        List<Integer> deptIds = userDeptList.stream().map(UserDept::getDeptId).collect(Collectors.toList());

        return deptService.listByIds(deptIds);
    }

    @Override
    public PageData<UserDTO> page(UserQuery query) {
        Page<User> page = new Page<>(query.getCurPage(), query.getPageSize());

        List<Integer> userIds = new ArrayList<>();

        // 如果deptId不为空
        if (query.getDeptId() != null) {
            List<Integer> thisAndChildIds = deptService.getThisAndChildIds(query.getDeptId());
            if (CollectionUtil.isEmpty(thisAndChildIds)) {
                return PageData.emptyData();
            }
            LambdaQueryWrapper<UserDept> udWrapper = new LambdaQueryWrapper<>();
            udWrapper.in(UserDept::getDeptId, thisAndChildIds);
            List<UserDept> list = userDeptService.list(udWrapper);
            if (CollectionUtil.isEmpty(list)) {
                return PageData.emptyData();
            }
            userIds = list.stream().map(UserDept::getUserId).collect(Collectors.toList());
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getNickname()), User::getNickname, query.getNickname());
        queryWrapper.like(StrUtil.isNotBlank(query.getPhone()), User::getPhone, query.getPhone());
        queryWrapper.like(StrUtil.isNotBlank(query.getUsername()), User::getUsername, query.getUsername());
        queryWrapper.in(query.getDeptId() != null, User::getId, userIds);
        page(page, queryWrapper);

        List<UserDTO> dtoList = afterQueryHandler(page.getRecords());

        return PageData.data(dtoList, page.getTotal(), page.getPages());
    }

    @Override
    public void afterDeleteHandler(User sysUser) {
        log.error("删除了{}", sysUser.toString());
    }

    @Override
    public void enabledSwitch(Integer id) {
        User entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("用户不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updateById(UserDTO dto) {
        User user = getById(dto.getId());

        if (user == null)
            throw new RuntimeException(StrUtil.format("{} 不存在", dto.getId()));

        // 添加用户与角色的关联
        roleService.addRelation(dto.getId(), dto.getRoleIdList());

        // 添加用户与部门的关联
        userDeptService.addRelation(dto.getId(), dto.getDeptIdList());

        BeanUtil.copyProperties(dto, user, "password");
        updateById(user);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User add(UserDTO dto) {
        String rawText = StrUtil.isNotBlank(dto.getPassword()) ? dto.getPassword() : wonderProperties.getUser().getDefaultPassword();
        String encode = BCrypt.hashpw(rawText);

        dto.setPassword(encode);

        User save = super.add(dto);

        // 添加用户与角色的关联
        roleService.addRelation(save.getId(), dto.getRoleIdList());

        // 添加用户与部门的关联
        userDeptService.addRelation(save.getId(), dto.getDeptIdList());

        return save;
    }

    @Override
    public UserDTO beforeAddOrUpdateHandler(UserDTO dto) {
        Assert.isFalse(existPhone(dto.getId(), dto.getPhone()), "手机号：{} 已存在", dto.getPhone());
        Assert.isFalse(existUsername(dto.getId(), dto.getUsername()), "{} 已存在", dto.getUsername());

        return dto;
    }

    @Override
    public UserDTO beforeUpdateHandler(UserDTO dto) {
        if (!(dto.getId().equals(SecurityUtil.ADMIN_USER_ID) && SecurityUtil.getLoginUser().getId().equals(SecurityUtil.ADMIN_USER_ID))) {
            throw new RuntimeException("只有超级管理员能编辑超级管理员");
        }

        return dto;
    }

    @Override
    public UserDTO beforeAddHandler(UserDTO dto) {
        List<Integer> roleIdList = dto.getRoleIdList();
        List<Role> roleList = roleService.listByIds(roleIdList);
        boolean match = roleList.stream().anyMatch(v -> v.getCode().equals(SecurityUtil.ADMIN_ROLE_CODE));
        Assert.isFalse(match, "所选角色不能包含超级管理员角色");

        return dto;
    }

    @Override
    public boolean existPhone(Integer userId, String phone) {
        Assert.notEmpty(phone);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existUsername(Integer userId, String username) {
        Assert.notEmpty(username);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public void resetPasswd(ResetPasswdDTO dto) {
        if (!isExist(dto.getId()))
            throw new RuntimeException("用户不存在：" + dto.getId());

        this.setUserPasswd(dto.getId(), dto.getPassword());
    }

    @Override
    public List<Integer> getDeptIds(Integer userId) {
        if (userId == null)
            return null;

        List<UserDept> userDeptList = userDeptService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userDeptList))
            return null;

        return userDeptList.stream().map(UserDept::getDeptId).collect(Collectors.toList());
    }

    @Override
    public List<Dept> getDepts(Integer userId) {
        List<Integer> deptIds = getDeptIds(userId);
        if (CollectionUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return deptService.listByIds(deptIds);
    }

    @Override
    public List<Integer> getRoleIds(Integer userId) {
        if (userId == null)
            return null;

        List<UserRole> userRoleList = userRoleService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userRoleList))
            return null;

        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public List<Role> getRoles(Integer userId) {
        List<Integer> roleIds = getRoleIds(userId);
        return roleService.listByIds(roleIds);
    }

    //    @Cacheable(cacheNames = "login_user_info", key = "#userId")
    @Override
    public LoginUser getLoginUserInfo(Integer userId) {
        User user = getById(userId);

        if (user == null) {
            return null;
        }

        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(user, loginUser);

        List<Integer> deptIds = getDeptIds(loginUser.getId());
        List<Integer> roleIds = getRoleIds(loginUser.getId());

        loginUser.setDepts(deptService.getDTOListIn(deptIds));
        loginUser.setRoles(roleService.getDTOListIn(roleIds));

        List<String> permissions = new ArrayList<>();
        for (Integer roleId : roleIds) {
            permissions.addAll(roleService.getPermissions(roleId));
        }
        loginUser.setPermissions(permissions);

        return loginUser;
    }

    @Override
    public User getByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        return getOne(queryWrapper);
    }

    @Override
    public void changPasswd(ChangePasswdDTO dto) {
        String dbPasswd = SecurityUtil.getLoginUser().getPassword();

        if (!BCrypt.checkpw(dto.getOldPasswd(), dbPasswd)) {
            throw new RuntimeException("原密码错误");
        }

        this.setUserPasswd(SecurityUtil.getLoginUser().getId(), dto.getNewPasswd());
    }

    @Override
    public boolean setUserPasswd(Integer userId, String rawText) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPassword, BCrypt.hashpw(rawText));
        updateWrapper.eq(User::getId, userId);
        return update(updateWrapper);
    }

}
