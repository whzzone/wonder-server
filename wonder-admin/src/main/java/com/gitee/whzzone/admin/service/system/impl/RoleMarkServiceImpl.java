package com.gitee.whzzone.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.RoleMarkMapper;
import com.gitee.whzzone.admin.pojo.dto.system.RoleMarkDto;
import com.gitee.whzzone.admin.pojo.entity.system.RoleMark;
import com.gitee.whzzone.admin.pojo.query.system.RoleMarkQuery;
import com.gitee.whzzone.admin.service.system.RoleMarkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
@Service
public class RoleMarkServiceImpl extends EntityServiceImpl<RoleMarkMapper, RoleMark, RoleMarkDto, RoleMarkQuery> implements RoleMarkService {

    @Override
    public List<RoleMark> getByRoleId(Long roleId){
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getId, roleId);
        return list(queryWrapper);
    }

    @Override
    public RoleMark getByRoleIdAndMarkId(Long roleId, Long markId) {
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getRoleId, roleId);
        queryWrapper.eq(RoleMark::getMarkId, markId);
        return getOne(queryWrapper);
    }
}
