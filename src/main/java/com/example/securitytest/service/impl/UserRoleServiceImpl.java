package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.UserRoleMapper;
import com.example.securitytest.pojo.entity.UserRole;
import com.example.securitytest.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/7/9
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
