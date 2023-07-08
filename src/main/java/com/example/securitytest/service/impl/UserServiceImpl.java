package com.example.securitytest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.UserMapper;
import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author :whz
 * @date : 2023/5/16 23:07
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public void verifyUser(User sysUser) {
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        if (sysUser.getDeleted()) {
            throw new RuntimeException("该账号已被删除");
        }

        if (!sysUser.getEnabled()) {
            throw new RuntimeException("该账号已被禁用");
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
    public UserDto afterQueryHandler(UserDto dto) {
        dto.setPassword("");
        return dto;
    }

    @Override
    public PageData<UserDto> page(UserQuery query) {
        Page<User> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getNickname()), User::getNickname, query.getNickname());
        queryWrapper.like(StrUtil.isNotBlank(query.getPhone()), User::getPhone, query.getPhone());
        queryWrapper.like(StrUtil.isNotBlank(query.getUsername()), User::getUsername, query.getUsername());
        page(page, queryWrapper);

        List<UserDto> sysUserDtos = BeanUtil.copyToList(page.getRecords(), UserDto.class);

        afterQueryHandler(sysUserDtos);

        PageData<UserDto> pageInfo = new PageData<>();
        pageInfo.setList(sysUserDtos);
        pageInfo.setPages(page.getPages());
        pageInfo.setTotal(page.getTotal());

        return pageInfo;
    }

    @Override
    public void afterDeleteHandler(User sysUser) {
        log.error("删除了{}", sysUser);
    }
}
