package com.gitee.whzzone.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.mapper.UserMapper;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.ResetPWDDto;
import com.gitee.whzzone.pojo.dto.UserDto;
import com.gitee.whzzone.pojo.query.UserQuery;
import com.gitee.whzzone.pojo.entity.*;
import com.gitee.whzzone.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author :whz
 * @date : 2023/5/16 23:07
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeptService userDeptService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.default-password}")
    private String defaultPassword;

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public void beforeLoginCheck(User user) {
        // 判断个人情况
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("用户不存在");
        if (user.getDeleted())
            throw new RuntimeException("该账号已被删除");
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
    public UserDto afterQueryHandler(UserDto dto) {
        dto.setPassword("");

        List<Dept> deptList = getUserDeptInfo(dto.getId());
        if (CollectionUtil.isNotEmpty(deptList)){
            dto.setDeptList(deptList);
            List<Long> ids = deptList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            dto.setDeptIdList(ids);
        }

        dto.setRoleIdList(roleService.getRoleIdsByUserId(dto.getId()));

        return dto;
    }

    @Override
    public List<Dept> getUserDeptInfo(Long userId) {
        List<UserDept> userDeptList = userDeptService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userDeptList))
            return new ArrayList<>();

        List<Long> deptIds = userDeptList.stream().map(UserDept::getDeptId).collect(Collectors.toList());

        return deptService.findInIds(deptIds);
    }

    @Override
    public Role getUserRoleInfo(Long userId) {
        // 查询部门
        LambdaQueryWrapper<UserRole> udQueryWrapper = new LambdaQueryWrapper<>();
        udQueryWrapper.eq(UserRole::getUserId, userId);
        udQueryWrapper.select(UserRole::getRoleId);
        UserRole userRole = userRoleService.getOne(udQueryWrapper);
        if (userRole != null) {
            return roleService.getById(userRole.getRoleId());
        }
        return null;
    }

    @Override
    public PageData<UserDto> page(UserQuery query) {
        Page<User> page = new Page<>(query.getCurPage(), query.getPageSize());

        List<Long> userIds = new ArrayList<>();

        // 如果deptId不为空
        if (query.getDeptId() != null) {
            List<Long> thisAndChildIds = deptService.getThisAndChildIds(query.getDeptId());
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

        List<UserDto> dtoList = BeanUtil.copyToList(page.getRecords(), UserDto.class);

        afterQueryHandler(dtoList);

        return new PageData<>(dtoList, page.getPages(), page.getTotal());
    }

    @Override
    public void afterDeleteHandler(User sysUser) {
        log.error("删除了{}", sysUser.toString());
    }

    @Override
    public void enabledSwitch(Long id) {
        User entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("用户不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Transactional
    @Override
    public boolean updateById(UserDto dto) {
        User user = getById(dto.getId());

        if (user == null)
            throw new RuntimeException(StrUtil.format("{} 不存在", dto.getId()));

        // 添加用户与角色的关联
        roleService.addRelation(dto.getId(), dto.getRoleIdList());

        // 添加用户与部门的关联
        userDeptService.addRelation(dto.getId(), dto.getDeptIdList());

        BeanUtil.copyProperties(dto, user, "password");

        return updateById(user);
    }

    @Transactional
    @Override
    public User save(UserDto dto) {
        if (StrUtil.isBlank(dto.getPassword())){
            dto.setPassword(passwordEncoder.encode(defaultPassword));
        }else {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User save = UserService.super.save(dto);

        // 添加用户与角色的关联
        roleService.addRelation(save.getId(), dto.getRoleIdList());

        // 添加用户与部门的关联
        userDeptService.addRelation(save.getId(), dto.getDeptIdList());

        return save;
    }

    @Override
    public UserDto beforeSaveHandler(UserDto dto) {
        Assert.isFalse(existSameUsername(dto.getId(), dto.getUsername()), "{} 已存在", dto.getUsername());
        Assert.isFalse(existSamePhone(dto.getId(), dto.getPhone()), "{} 已存在", dto.getPhone());
        Assert.isFalse(existSameEmail(dto.getId(), dto.getEmail()), "{} 已存在", dto.getEmail());

        return dto;
    }

    @Override
    public boolean existSameUsername(Long userId, String username) {
        Assert.notEmpty(username);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSamePhone(Long userId, String phone) {
        Assert.notEmpty(phone);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean existSameEmail(Long userId, String email) {
        if (StrUtil.isBlank(email))
            return false;

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.ne(userId != null, User::getId, userId);
        return count(queryWrapper) > 0;
    }

    @Override
    public void resetPWD(ResetPWDDto dto) {
        if (StrUtil.isBlank(dto.getPassword()))
            throw new RuntimeException("新密码不能为空");

        if (!isExist(dto.getId()))
            throw new RuntimeException("用户不存在：" + dto.getId());

        String encode = passwordEncoder.encode(dto.getPassword());

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPassword, encode);
        updateWrapper.eq(User::getId, dto.getId());
        update(updateWrapper);
    }

    @Override
    public List<Long> getDeptIds(Long userId) {
        if (userId == null)
            return null;

        List<UserDept> userDeptList = userDeptService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userDeptList))
            return null;

        return userDeptList.stream().map(UserDept::getDeptId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getRoleIds(Long userId) {
        if (userId == null)
            return null;

        List<UserRole> userRoleList = userRoleService.getByUserId(userId);
        if (CollectionUtil.isEmpty(userRoleList))
            return null;

        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
