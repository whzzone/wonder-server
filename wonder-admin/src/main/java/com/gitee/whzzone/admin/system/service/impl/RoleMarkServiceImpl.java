package com.gitee.whzzone.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.RoleMark;
import com.gitee.whzzone.admin.system.mapper.RoleMarkMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMarkDto;
import com.gitee.whzzone.admin.system.pojo.query.RoleMarkQuery;
import com.gitee.whzzone.admin.system.service.RoleMarkService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
@Service
public class RoleMarkServiceImpl extends EntityServiceImpl<RoleMarkMapper, RoleMark, RoleMarkDto, RoleMarkQuery> implements RoleMarkService {

    @Override
    public List<RoleMark> getByRoleId(Integer roleId){
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getId, roleId);
        return list(queryWrapper);
    }

    @Override
    public List<RoleMark> getByRoleIdAndMarkId(Integer roleId, Integer markId) {
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getRoleId, roleId);
        queryWrapper.eq(RoleMark::getMarkId, markId);
        return list(queryWrapper);
    }
}
