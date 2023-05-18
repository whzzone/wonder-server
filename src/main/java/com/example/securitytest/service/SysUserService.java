package com.example.securitytest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.securitytest.pojo.entity.SysUser;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getByEmail(String email);

    void verifyUser(SysUser sysUser);
}
