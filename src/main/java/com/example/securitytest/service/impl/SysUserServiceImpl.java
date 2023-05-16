package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.entity.SysUser;
import com.example.securitytest.mapper.SysUserMapper;
import com.example.securitytest.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author :whz
 * @date : 2023/5/16 23:07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
