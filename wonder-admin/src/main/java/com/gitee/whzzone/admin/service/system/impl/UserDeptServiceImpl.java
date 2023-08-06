package com.gitee.whzzone.admin.service.system.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.UserDeptMapper;
import com.gitee.whzzone.admin.pojo.dto.system.UserDeptDto;
import com.gitee.whzzone.admin.pojo.entity.system.UserDept;
import com.gitee.whzzone.admin.pojo.query.system.UserDeptQuery;
import com.gitee.whzzone.admin.service.system.DeptService;
import com.gitee.whzzone.admin.service.system.UserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Validated
@Service
public class UserDeptServiceImpl extends EntityServiceImpl<UserDeptMapper, UserDept, UserDeptDto, UserDeptQuery> implements UserDeptService {

    @Autowired
    private DeptService deptService;

    @Override
    public void addRelation(Long userId, Long deptId) {

        Assert.isTrue(deptService.isExist(deptId), "部门不存在");

        // 删除关联
        removeRelation(userId);

        // 新增关联
        UserDept userDept = new UserDept();
        userDept.setUserId(userId);
        userDept.setDeptId(deptId);
        save(userDept);
    }

    @Override
    public void addRelation(Long userId, List<Long> deptIds) {
        if (!deptService.existAll(deptIds)){
            throw new RuntimeException("所选部门有不存在的部门id");
        }

        // 删除关联
        removeRelation(userId);

        for (Long deptId : deptIds) {
            UserDept userDept = new UserDept();
            userDept.setUserId(userId);
            userDept.setDeptId(deptId);
            save(userDept);
        }
    }

    @Override
    public void removeRelation(Long userId){
        remove(new LambdaQueryWrapper<UserDept>().eq(UserDept::getUserId, userId));
    }

    @Override
    public List<UserDept> getByUserId(Long userId) {
        LambdaQueryWrapper<UserDept> udQueryWrapper = new LambdaQueryWrapper<>();
        udQueryWrapper.eq(UserDept::getUserId, userId);
        udQueryWrapper.select(UserDept::getDeptId);
        return list(udQueryWrapper);
    }
}
