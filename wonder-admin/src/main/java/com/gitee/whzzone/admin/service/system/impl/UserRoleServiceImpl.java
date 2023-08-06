package com.gitee.whzzone.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.UserRoleMapper;
import com.gitee.whzzone.admin.pojo.dto.system.UserRoleDto;
import com.gitee.whzzone.admin.pojo.entity.system.UserRole;
import com.gitee.whzzone.admin.pojo.query.system.UserRoleQuery;
import com.gitee.whzzone.admin.service.system.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Service
public class UserRoleServiceImpl extends EntityServiceImpl<UserRoleMapper, UserRole, UserRoleDto, UserRoleQuery> implements UserRoleService {

    @Override
    public List<UserRole> getByUserId(Long userId) {
        if (userId == null)
            return null;

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return list(queryWrapper);
    }
}
