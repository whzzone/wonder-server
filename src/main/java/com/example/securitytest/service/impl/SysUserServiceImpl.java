package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.mapper.SysUserMapper;
import com.example.securitytest.service.SysUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author :whz
 * @date : 2023/5/16 23:07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public SysUser getByEmail(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public void verifyUser(SysUser sysUser) {
        if (Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("用户不存在");
        }

        if (sysUser.getDeleted()){
            throw new RuntimeException("该账号已被删除");
        }

        if (!sysUser.getEnabled()){
            throw new RuntimeException("该账号已被禁用");
        }
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return getOne(queryWrapper);
    }
}
