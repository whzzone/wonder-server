package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.mapper.RuleMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import com.gitee.whzzone.admin.system.pojo.query.RuleQuery;
import com.gitee.whzzone.admin.system.service.RoleMarkService;
import com.gitee.whzzone.admin.system.service.RuleService;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
@Service
public class RuleServiceImpl extends EntityServiceImpl<RuleMapper, Rule, RuleDTO, RuleQuery> implements RuleService {

    @Autowired
    private RoleMarkService roleMarkService;

    @Override
    public List<RuleDTO> getByMarkId(Integer markId) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getMarkId, markId);
        List<Rule> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();

        return BeanUtil.copyToList(list, RuleDTO.class);
    }

    @Override
    public RuleDTO getByRoleIdAndMarkId(Integer roleId, Integer markId) {
        /*List<RoleMark> roleMarkList = roleMarkService.getByRoleIdAndMarkId(roleId, markId);
        if (roleMark != null){
            Long ruleId = roleMark.getRuleId();
            return afterQueryHandler(getById(ruleId));
        }*/
        return null;
    }

    @Override
    public List<Rule> getByIds(List<Integer> ruleIds) {
        if (CollectionUtil.isEmpty(ruleIds)) {
            return null;
        }
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BaseEntity::getId, ruleIds);
        return list(queryWrapper);
    }
}
