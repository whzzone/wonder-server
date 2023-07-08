package com.example.securitytest.common.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author : whz
 * @date : 2023/5/16 19:27
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User sysUser = sysUserService.getOne(queryWrapper);
        if (Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("用户名不存在");
        }

        // TODO: 2023/5/16 查询用户权限
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUsername(), sysUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
