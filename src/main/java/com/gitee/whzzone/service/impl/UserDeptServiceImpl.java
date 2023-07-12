package com.gitee.whzzone.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.mapper.UserDeptMapper;
import com.gitee.whzzone.pojo.entity.UserDept;
import com.gitee.whzzone.service.DeptService;
import com.gitee.whzzone.service.UserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author Create by whz at 2023/7/9
 */

@Validated
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

    @Autowired
    private DeptService deptService;

    @Override
    public void addRelation(@NotNull Long userId, @NotNull Long deptId) {

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
    public void removeRelation(@NotNull Long userId){
        remove(new LambdaQueryWrapper<UserDept>().eq(UserDept::getUserId, userId));
    }
}
