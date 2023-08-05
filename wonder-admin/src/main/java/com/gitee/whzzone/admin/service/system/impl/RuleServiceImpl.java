package com.gitee.whzzone.admin.service.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.admin.mapper.system.RuleMapper;
import com.gitee.whzzone.admin.pojo.dto.system.RuleDto;
import com.gitee.whzzone.admin.pojo.entity.system.RoleMark;
import com.gitee.whzzone.admin.pojo.entity.system.Rule;
import com.gitee.whzzone.admin.service.system.RoleMarkService;
import com.gitee.whzzone.admin.service.system.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements RuleService {

    @Autowired
    private RoleMarkService roleMarkService;

    @Override
    public List<RuleDto> getByMarkId(Long markId) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getMarkId, markId);
        List<Rule> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();

        return BeanUtil.copyToList(list, RuleDto.class);
    }

    @Override
    public RuleDto getByRoleIdAndMarkId(Long roleId, Long markId) {
        RoleMark roleMark = roleMarkService.getByRoleIdAndMarkId(roleId, markId);
        if (roleMark != null){
            Long ruleId = roleMark.getRuleId();
            return afterQueryHandler(getById(ruleId));
        }
        return null;
    }
}
