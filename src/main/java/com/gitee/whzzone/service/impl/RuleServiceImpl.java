package com.gitee.whzzone.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.mapper.RuleMapper;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.entity.RoleMark;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.service.RoleMarkService;
import com.gitee.whzzone.service.RuleService;
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
            return getDtoById(ruleId);
        }
        return null;
    }
}
