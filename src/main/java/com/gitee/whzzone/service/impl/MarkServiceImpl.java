package com.gitee.whzzone.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.mapper.MarkMapper;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.dto.MarkDto;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.entity.RoleMark;
import com.gitee.whzzone.pojo.entity.Mark;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.pojo.query.MarkQuery;
import com.gitee.whzzone.service.RoleMarkService;
import com.gitee.whzzone.service.RoleService;
import com.gitee.whzzone.service.RuleService;
import com.gitee.whzzone.service.MarkService;
import com.gitee.whzzone.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class MarkServiceImpl extends ServiceImpl<MarkMapper, Mark> implements MarkService {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RoleMarkService roleMarkService;

    @Autowired
    private RoleService roleService;

    @Override
    public Rule getByName(String name) {
        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mark::getName, name);
        Mark mark = getOne(queryWrapper);
        if (mark == null)
            throw new RuntimeException("不存在：" + name);

        Long roleId = SecurityUtil.getPriorRoleId();
        if (!roleService.isExist(roleId))
            throw new RuntimeException("角色不存在");

        // 角色 与 标记 的关联
        RoleMark roleMark = roleMarkService.getByRoleIdAndMarkId(roleId, mark.getId());
        if (roleMark == null)
            throw new RuntimeException("角色 与 标记 的关联为空");

        Rule rule = ruleService.getById(roleMark.getRuleId());
        if (rule == null)
            throw new RuntimeException("不存在具体配置, Rule 为空");

        return rule;
    }

    private Object parseArgValues(Class<?> type, String s) {
        if (type == String.class) {
            return s;
        } else if (type == Integer.class || type == int.class) {
            return Integer.parseInt(s);
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(s);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(s);
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(s);
        }

        throw new IllegalArgumentException("Cannot convert argument to type " + type.getName());
    }

    @Override
    public DataScopeInfo execRuleByEntity(Rule entity) {
        return execRuleHandler(entity);
    }

    private DataScopeInfo execRuleHandler(Rule rule) {
        if (rule == null)
            throw new RuntimeException("数据规则不存在");
        RuleDto dto = new RuleDto();
        BeanUtil.copyProperties(rule, dto);
        DataScopeInfo info = new DataScopeInfo();
        info.setDto(dto);

        if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
            info.setDto(dto);
            return info;
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
                        argValues[i] = parseArgValues(paramsTypes[i], actualArray[i]);
                    }
                }

                Class<?> clazz = Class.forName(rule.getClassName());

                Method targetMethod = clazz.getDeclaredMethod(rule.getMethodName(), paramsTypes);
                if (Modifier.isStatic(targetMethod.getModifiers())) {
                    // 设置静态方法可访问
                    targetMethod.setAccessible(true);
                    // 执行静态方法
                    info.setIdList((List<Long>) targetMethod.invoke(null, argValues));
                    return info;
                } else {
                    // 创建类实例
                    Object obj = clazz.newInstance();
                    // 执行方法
                    info.setIdList((List<Long>) targetMethod.invoke(obj, argValues));
                    return info;
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("配置了不存在的方法");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("配置了不存在的类");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("其他错误：" + e.getMessage());
            }

        } else throw new RuntimeException("错误的提供类型");
    }

    @Override
    public DataScopeInfo execRuleByName(String name) {
        Rule rule = getByName(name);
        return execRuleHandler(rule);
    }

    @Override
    public MarkDto beforeSaveHandler(MarkDto dto) {
        if (existSameName(dto.getId(), dto.getName()))
            throw new RuntimeException("存在相同的名称：" + dto.getName());

        return MarkService.super.beforeSaveHandler(dto);
    }

    @Override
    public MarkDto beforeUpdateHandler(MarkDto dto) {
        if (existSameName(dto.getId(), dto.getName()))
            throw new RuntimeException("存在相同的名称：" + dto.getName());

        return MarkService.super.beforeUpdateHandler(dto);
    }

    @Override
    public boolean existSameName(Long id, String scopeName){
        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mark::getName, scopeName);
        queryWrapper.ne(id != null, Mark::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public PageData<MarkDto> page(MarkQuery query) {
        Page<Mark> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Mark> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(query.getName()))
            queryWrapper.like(Mark::getName, query.getName());

        page(page, queryWrapper);

        List<MarkDto> list = BeanUtil.copyToList(page.getRecords(), MarkDto.class);
        afterQueryHandler(list);
        return new PageData<>(list, page.getTotal(), page.getPages());
    }

    @Override
    public void enabledSwitch(Long id) {
        Mark entity = getById(id);
        if (entity == null){
            throw new RuntimeException("不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

//    @Override
//    public MarkDto afterQueryHandler(MarkDto dto) {
//        if (dto.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())){
//            if (StrUtil.isNotBlank(dto.getFormalParam()) && StrUtil.isNotBlank(dto.getActualParam())) {
//                String[] formalParamSplit = dto.getFormalParam().split(";");
//                String[] actualParamSplit = dto.getActualParam().split(";");
//                // dto.setFormalParamList(dto.getFormalParam().split(";"));
//                // dto.setActualParamList(dto.getActualParam().split(";"));
//                List<ParamDto> paramList = new ArrayList<>();
//                for (int i = 0; i < formalParamSplit.length; i++) {
//                    paramList.add(new ParamDto(formalParamSplit[i], actualParamSplit[i]));
//                }
//                dto.setParamList(paramList);
//            }
//
//        }
//
//        return dto;
//    }
}
