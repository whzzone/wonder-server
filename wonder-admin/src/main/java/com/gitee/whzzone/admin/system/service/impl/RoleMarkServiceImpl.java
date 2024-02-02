package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.RoleMark;
import com.gitee.whzzone.admin.system.mapper.RoleMarkMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMarkDTO;
import com.gitee.whzzone.admin.system.pojo.query.RoleMarkQuery;
import com.gitee.whzzone.admin.system.service.RoleMarkService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/7/16
 */
@Service
public class RoleMarkServiceImpl extends EntityServiceImpl<RoleMarkMapper, RoleMark, RoleMarkDTO, RoleMarkQuery> implements RoleMarkService {

    @Override
    public List<RoleMark> getByRoleId(Integer roleId) {
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

    @Override
    public List<RoleMark> getByRoleIdsAndMarkId(List<Integer> roleIds, Integer markId) {
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleMark::getRoleId, roleIds);
        queryWrapper.eq(RoleMark::getMarkId, markId);
        List<RoleMark> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        // 根据 ruleId 去重
        return list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(RoleMark::getRuleId)))
                        , ArrayList::new
                )
        );
    }
}
