package com.gitee.whzzone.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.admin.mapper.system.UserRoleMapper;
import com.gitee.whzzone.admin.pojo.entity.system.UserRole;
import com.gitee.whzzone.admin.service.system.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRole> getByUserId(Long userId) {
        if (userId == null)
            return null;

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return list(queryWrapper);
    }
}
