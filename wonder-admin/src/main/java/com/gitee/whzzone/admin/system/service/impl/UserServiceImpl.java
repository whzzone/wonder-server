package com.gitee.whzzone.admin.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.entity.UserDept;
import com.gitee.whzzone.admin.system.entity.UserRole;
import com.gitee.whzzone.admin.system.mapper.UserMapper;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPWDDTO;
import com.gitee.whzzone.admin.system.pojo.dto.UserDTO;
import com.gitee.whzzone.admin.system.pojo.query.UserQuery;
import com.gitee.whzzone.admin.system.service.*;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public void loginCheck(User user) {
        // 判断个人情况
        if (Objects.isNull(user))
            throw new RuntimeException("该账号不存在");
        if (!user.getEnabled())
            throw new RuntimeException("该账号已被禁止登录");

        // 判断角色情况
        /*Role roleInfo = getUserRoleInfo(user.getId());
        if (roleInfo.getDeleted())
            throw new RuntimeException("该账号所属角色已被删除");
        if (!roleInfo.getEnabled())
            throw new RuntimeException("该账号所属角色已被禁止登录");*/

        // 判断部门情况 FIXME 登录判断
        /*Dept deptInfo = getUserDeptInfo(user.getId());
        if (deptInfo.getDeleted())
            throw new RuntimeException("该账号所属部门已被删除");
        if (!deptInfo.getEnabled())
            throw new RuntimeException("该账号所属部门已被禁止登录");*/
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

        List<Dept> deptList = getUserDeptInfo(dto.getId());
        if (CollectionUtil.isNotEmpty(deptList)){
            dto.setDeptList(deptList);
            List<Integer> ids = deptList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            dto.setDeptIdList(ids);
        }

        dto.setRoleIdList(roleService.getRoleIdsByUserId(dto.getId()));

        return dto;
    }

    @Override
    public List<Dept> getUserDeptInfo(Integer userId) {
        List<UserDept> userDeptList = userDeptService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userDeptList))
            return new ArrayList<>();

        List<Integer> deptIds = userDeptList.stream().map(UserDept::getDeptId).collect(Collectors.toList());

        return deptService.findInIds(deptIds);
    }

    @Override
    public PageData<UserDTO> page(UserQuery query) {
        Page<User> page = new Page<>(query.getCurPage(), query.getPageSize());

        List<Integer> userIds = new ArrayList<>();

        // 如果deptId不为空
        if (query.getDeptId() != null) {
            List<Integer> thisAndChildIds = deptService.getThisAndChildIds(query.getDeptId());
            if (CollectionUtil.isEmpty(thisAndChildIds)) {
                return new PageData<>();
            }
            LambdaQueryWrapper<UserDept> udWrapper = new LambdaQueryWrapper<>();
            udWrapper.in(UserDept::getDeptId, thisAndChildIds);
            List<UserDept> list = userDeptService.list(udWrapper);
            if (CollectionUtil.isEmpty(list)) {
                return new PageData<>();
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

        return new PageData<>(dtoList, page.getPages(), page.getTotal());
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

    @Transactional
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

    @Transactional
    @Override
    public User add(UserDTO dto) {
        String encode = BCrypt.hashpw(StrUtil.isNotBlank(dto.getPassword()) ?
                dto.getPassword() : wonderProperties.getUser().getDefaultPassword(),
                BCrypt.gensalt());

        dto.setPassword(encode);

        User save = super.add(dto);

        // 添加用户与角色的关联
        roleService.addRelation(save.getId(), dto.getRoleIdList());

        // 添加用户与部门的关联
        userDeptService.addRelation(save.getId(), dto.getDeptIdList());

        return save;
    }

    @Override
    public UserDTO beforeAddHandler(UserDTO dto) {
        Assert.isFalse(existSameUsername(dto.getId(), dto.getUsername()), "{} 已存在", dto.getUsername());
        Assert.isFalse(existSamePhone(dto.getId(), dto.getPhone()), "{} 已存在", dto.getPhone());
        Assert.isFalse(existSameEmail(dto.getId(), dto.getEmail()), "{} 已存在", dto.getEmail());

        return dto;
    }

    @Override
    public boolean existSameUsername(Integer userId, String username) {
        Assert.notEmpty(username);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSamePhone(Integer userId, String phone) {
        Assert.notEmpty(phone);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameEmail(Integer userId, String email) {
        if (StrUtil.isBlank(email))
            return false;

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public void resetPWD(ResetPWDDTO dto) {
        if (StrUtil.isBlank(dto.getPassword()))
            throw new RuntimeException("新密码不能为空");

        if (!isExist(dto.getId()))
            throw new RuntimeException("用户不存在：" + dto.getId());

        String encode = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPassword, encode);
        updateWrapper.eq(User::getId, dto.getId());
        update(updateWrapper);
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
    public List<Integer> getRoleIds(Integer userId) {
        if (userId == null)
            return null;

        List<UserRole> userRoleList = userRoleService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userRoleList))
            return null;

        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "login_user_info", key = "#userId")
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

}
