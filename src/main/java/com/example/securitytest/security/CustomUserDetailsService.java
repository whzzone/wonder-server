package com.example.securitytest.security;

import com.example.securitytest.entity.SysUser;
import com.example.securitytest.mapper.SysUserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author : whz
 * @date : 2023/5/16 19:27
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.getByUsername(username);
        User user = new User("username", "password", new ArrayList<>());
        return user;
    }
}
