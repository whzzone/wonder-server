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

import java.util.HashSet;
import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Validated
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

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
        HashSet<Long> ids = new HashSet<>(deptIds);
        if (!deptService.existAll(ids)){
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
