package com.example.securitytest.common.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.securitytest.entity.SysUser;
import com.example.securitytest.mapper.SysUserMapper;
import com.example.securitytest.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author : whz
 * @date : 2023/5/16 19:27
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if (Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("用户名不存在");
        }

        // TODO: 2023/5/16 查询用户权限
        User user = new User(sysUser.getUsername(), sysUser.getPassword(), new ArrayList<>());
        return user;
    }
}
