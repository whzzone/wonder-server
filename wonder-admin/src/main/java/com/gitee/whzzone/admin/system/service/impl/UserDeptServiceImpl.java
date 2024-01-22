package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.UserDept;
import com.gitee.whzzone.admin.system.mapper.UserDeptMapper;
import com.gitee.whzzone.admin.system.pojo.dto.UserDeptDTO;
import com.gitee.whzzone.admin.system.pojo.query.UserDeptQuery;
import com.gitee.whzzone.admin.system.service.DeptService;
import com.gitee.whzzone.admin.system.service.UserDeptService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */

@Validated
@Service
public class UserDeptServiceImpl extends EntityServiceImpl<UserDeptMapper, UserDept, UserDeptDTO, UserDeptQuery> implements UserDeptService {

    @Autowired
    private DeptService deptService;

    @Override
    public void addRelation(Integer userId, Integer deptId) {

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
    public void addRelation(Integer userId, List<Integer> deptIds) {
        if (!deptService.existAll(deptIds)){
            throw new RuntimeException("所选部门有不存在的部门id");
        }

        // 删除关联
        removeRelation(userId);

        for (Integer deptId : deptIds) {
            UserDept userDept = new UserDept();
            userDept.setUserId(userId);
            userDept.setDeptId(deptId);
            save(userDept);
        }
    }

    @Override
    public void removeRelation(Integer userId){
        remove(new LambdaQueryWrapper<UserDept>().eq(UserDept::getUserId, userId));
    }

    @Override
    public List<UserDept> getByUserId(Integer userId) {
        LambdaQueryWrapper<UserDept> udQueryWrapper = new LambdaQueryWrapper<>();
        udQueryWrapper.eq(UserDept::getUserId, userId);
        udQueryWrapper.select(UserDept::getDeptId);
        return list(udQueryWrapper);
    }
}
