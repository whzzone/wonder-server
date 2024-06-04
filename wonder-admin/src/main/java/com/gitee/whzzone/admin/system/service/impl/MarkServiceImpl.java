package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import com.gitee.whzzone.admin.system.entity.Mark;
import com.gitee.whzzone.admin.system.entity.RoleMark;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.mapper.MarkMapper;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.MarkDTO;
import com.gitee.whzzone.admin.system.pojo.query.MarkQuery;
import com.gitee.whzzone.admin.system.service.MarkService;
import com.gitee.whzzone.admin.system.service.RoleMarkService;
import com.gitee.whzzone.admin.system.service.RoleService;
import com.gitee.whzzone.admin.system.service.RuleService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class MarkServiceImpl extends EntityServiceImpl<MarkMapper, Mark, MarkDTO, MarkQuery> implements MarkService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RoleMarkService roleMarkService;

    @Autowired
    private RoleService roleService;

    @Override
    public List<Rule> getByName(String name) {
        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mark::getName, name);
        Mark mark = getOne(queryWrapper);
        if (mark == null)
            throw new RuntimeException("不存在：" + name);

        List<Integer> roleIds = SecurityUtil.getLoginUser().getRoleIds();

        // 角色 与 标记 的关联
        List<RoleMark> roleMarkList = roleMarkService.getByRoleIdsAndMarkId(roleIds, mark.getId());

        if (CollectionUtil.isEmpty(roleMarkList))
            return null;

        List<Integer> ruleIds = roleMarkList.stream().map(RoleMark::getRuleId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(ruleIds)) {
            return Collections.emptyList();
        }

        return ruleService.listByIds(ruleIds);
    }

    private DataScopeInfo execRuleHandler(List<Rule> rules) {
        if (CollectionUtil.isEmpty(rules))
            return null;

        List<DataScopeRule> ruleList = new ArrayList<>();

        for (Rule rule : rules) {
            DataScopeRule dataScopeRule = new DataScopeRule();
            BeanUtil.copyProperties(rule, dataScopeRule);

            if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
                ruleList.add(dataScopeRule);

            } else if (rule.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
                try {
                    Class<?>[] paramsTypes = null;
                    Object[] argValues = null;

                    if (StrUtil.isNotBlank(rule.getFormalParam()) && StrUtil.isNotBlank(rule.getActualParam())) {
                        // 获取形参数组
                        String[] formalArray = rule.getFormalParam().split(";");
                        // 获取实参数组
                        String[] actualArray = rule.getActualParam().split(";");

                        if (formalArray.length != actualArray.length)
                            throw new RuntimeException("形参数量与实参数量不符合");

                        // 转换形参为Class数组
                        paramsTypes = new Class<?>[formalArray.length];
                        for (int i = 0; i < formalArray.length; i++) {
                            paramsTypes[i] = Class.forName(formalArray[i].trim());
                        }

                        // 转换实参为Object数组
                        argValues = new Object[actualArray.length];
                        for (int i = 0; i < actualArray.length; i++) {
                            argValues[i] = JSONObject.parseObject(actualArray[i], paramsTypes[i]);
                        }
                    }

                    String[] parts = rule.getFullMethodName().split("#");
                    String className = parts[0];
                    String methodName = parts[1];

                    Class<?> clazz = Class.forName(className);
                    Object result;

                    Method targetMethod = clazz.getDeclaredMethod(methodName, paramsTypes);
                    if (Modifier.isStatic(targetMethod.getModifiers())) {
                        // 设置静态方法可访问
                        targetMethod.setAccessible(true);
                        // 执行静态方法
                        result = targetMethod.invoke(null, argValues);
                    } else {
                        try {
                            // 尝试从容器中获取实例
                            Object instance = context.getBean(Class.forName(className));
                            Class<?> beanClazz = instance.getClass();
                            Method beanClazzMethod = beanClazz.getDeclaredMethod(methodName, paramsTypes);

                            // 执行方法
                            result = beanClazzMethod.invoke(instance, argValues);

                        } catch (NoSuchBeanDefinitionException e) {
                            // 创建类实例
                            Object obj = clazz.newInstance();
                            // 执行方法
                            result = targetMethod.invoke(obj, argValues);
                        }
                    }

                    dataScopeRule.setResult(result);
                    ruleList.add(dataScopeRule);

                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("配置了不存在的方法");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("配置了不存在的类");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("其他错误：" + e.getMessage());
                }

            } else
                throw new RuntimeException("错误的提供类型");
        }
        DataScopeInfo dataScopeInfo = new DataScopeInfo();
        dataScopeInfo.setRuleList(ruleList);
        return dataScopeInfo;
    }

    @Override
    public DataScopeInfo execRuleByName(String name) {
        return execRuleHandler(getByName(name));
    }

    @Override
    public MarkDTO beforeAddOrUpdateHandler(MarkDTO dto) {
        if (existSameName(dto.getId(), dto.getName()))
            throw new RuntimeException("存在相同的名称：" + dto.getName());
        return dto;
    }

    @Override
    public boolean existSameName(Integer id, String scopeName) {
        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mark::getName, scopeName);
        queryWrapper.ne(id != null, Mark::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public PageData<MarkDTO> page(MarkQuery query) {
        Page<Mark> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Mark::getSort);
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Mark::getName, query.getName());

        page(page, queryWrapper);

        List<MarkDTO> list = afterQueryHandler(page.getRecords());
        return PageData.data(list, page.getTotal(), page.getPages());
    }

    @Override
    public void enabledSwitch(Integer id) {
        Mark entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Override
    public List<MarkDTO> list(MarkQuery query) {
        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Mark::getName, query.getName());
        queryWrapper.orderByAsc(Mark::getSort);
        return afterQueryHandler(list(queryWrapper));
    }

    @Override
    public void removeAllByRoleIdAndMarkId(Integer roleId, Integer markId) {
        if (roleId == null)
            throw new RuntimeException("roleId不能为空");
        if (markId == null)
            throw new RuntimeException("markId不能为空");

        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getRoleId, roleId);
        queryWrapper.eq(RoleMark::getMarkId, markId);
        roleMarkService.remove(queryWrapper);
    }

    @Override
    public boolean addRelation(Integer roleId, Integer markId, Integer ruleId) {
        if (roleId == null)
            throw new RuntimeException("roleId不能为空");

        if (markId == null)
            throw new RuntimeException("markId不能为空");

        if (ruleId == null)
            throw new RuntimeException("ruleId不能为空");

        RoleMark entity = new RoleMark();
        entity.setRoleId(roleId);
        entity.setMarkId(markId);
        entity.setRuleId(ruleId);
        return roleMarkService.save(entity);
    }

    @Override
    public void removeRelation(Integer roleId, Integer ruleId) {
        LambdaQueryWrapper<RoleMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMark::getRoleId, roleId);
        queryWrapper.eq(RoleMark::getRuleId, ruleId);
        roleMarkService.remove(queryWrapper);
    }

    //    @Override
//    public MarkDTO afterQueryHandler(MarkDTO dto) {
//        if (dto.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())){
//            if (StrUtil.isNotBlank(dto.getFormalParam()) && StrUtil.isNotBlank(dto.getActualParam())) {
//                String[] formalParamSplit = dto.getFormalParam().split(";");
//                String[] actualParamSplit = dto.getActualParam().split(";");
//                // dto.setFormalParamList(dto.getFormalParam().split(";"));
//                // dto.setActualParamList(dto.getActualParam().split(";"));
//                List<ParamDTO> paramList = new ArrayList<>();
//                for (int i = 0; i < formalParamSplit.length; i++) {
//                    paramList.add(new ParamDTO(formalParamSplit[i], actualParamSplit[i]));
//                }
//                dto.setParamList(paramList);
//            }
//
//        }
//
//        return dto;
//    }
}
