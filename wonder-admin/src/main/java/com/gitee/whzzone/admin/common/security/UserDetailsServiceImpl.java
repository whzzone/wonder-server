package com.gitee.whzzone.admin.common.security;

import com.gitee.whzzone.admin.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : whz
 * @date : 2023/5/16 19:27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = userService.getLoginUserInfo(username);

        // 判断个人情况
        if (Objects.isNull(loginUser))
            throw new UsernameNotFoundException("该账号不存在");
        if (loginUser.getDeleted())
            throw new RuntimeException("该账号已被删除");
        if (!loginUser.getEnabled())
            throw new RuntimeException("该账号已被禁止登录");

        // 待处理多部门多角色

        return loginUser;
    }
}
