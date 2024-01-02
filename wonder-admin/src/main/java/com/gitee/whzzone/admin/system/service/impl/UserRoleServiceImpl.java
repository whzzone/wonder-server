package com.gitee.whzzone.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.UserRole;
import com.gitee.whzzone.admin.system.mapper.UserRoleMapper;
import com.gitee.whzzone.admin.system.pojo.dto.UserRoleDto;
import com.gitee.whzzone.admin.system.pojo.query.UserRoleQuery;
import com.gitee.whzzone.admin.system.service.UserRoleService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Service
public class UserRoleServiceImpl extends EntityServiceImpl<UserRoleMapper, UserRole, UserRoleDto, UserRoleQuery> implements UserRoleService {

    @Override
    public List<UserRole> getByUserId(Integer userId) {
        if (userId == null)
            return null;

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return list(queryWrapper);
    }
}
