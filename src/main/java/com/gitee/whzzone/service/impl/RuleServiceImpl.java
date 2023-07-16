package com.gitee.whzzone.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.mapper.RuleMapper;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.ParamDto;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.pojo.query.RuleQuery;
import com.gitee.whzzone.service.RuleService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements RuleService {

    @Override
    public Rule getByName(String name) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getName, name);
        return getOne(queryWrapper);
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

    private DataScopeInfo execRuleHandler(Rule entity) {
        if (entity == null)
            throw new RuntimeException("数据规则不存在");
        RuleDto dto = new RuleDto();
        BeanUtil.copyProperties(entity, dto);
        DataScopeInfo info = new DataScopeInfo();
        info.setDto(dto);

        if (entity.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
            info.setDto(dto);
            return info;
        } else if (entity.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
            try {
                Class<?>[] paramsTypes = null;
                Object[] argValues = null;

                if (StrUtil.isNotBlank(entity.getFormalParam()) && StrUtil.isNotBlank(entity.getActualParam())) {
                    // 获取形参数组
                    String[] formalArray = entity.getFormalParam().split(";");
                    // 获取实参数组
                    String[] actualArray = entity.getActualParam().split(";");

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

                Class<?> clazz = Class.forName(entity.getClassName());

                Method targetMethod = clazz.getDeclaredMethod(entity.getMethodName(), paramsTypes);
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
        Rule entity = getByName(name);
        return execRuleHandler(entity);
    }

    @Override
    public RuleDto beforeSaveHandler(RuleDto dto) {
        if (existSameName(dto.getId(), dto.getName()))
            throw new RuntimeException("存在相同的名称：" + dto.getName());

        return RuleService.super.beforeSaveHandler(dto);
    }

    @Override
    public RuleDto beforeUpdateHandler(RuleDto dto) {
        if (existSameName(dto.getId(), dto.getName()))
            throw new RuntimeException("存在相同的名称：" + dto.getName());

        return RuleService.super.beforeUpdateHandler(dto);
    }

    @Override
    public boolean existSameName(Long id, String scopeName){
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getName, scopeName);
        queryWrapper.ne(id != null, Rule::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public PageData<RuleDto> page(RuleQuery query) {
        Page<Rule> page = new Page<>(query.getCurPage(), query.getPageSize());

        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(query.getName()))
            queryWrapper.like(Rule::getName, query.getName());

        page(page, queryWrapper);

        List<RuleDto> list = BeanUtil.copyToList(page.getRecords(), RuleDto.class);
        afterQueryHandler(list);
        return new PageData<>(list, page.getTotal(), page.getPages());
    }

    @Override
    public void enabledSwitch(Long id) {
        Rule entity = getById(id);
        if (entity == null){
            throw new RuntimeException("不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Override
    public RuleDto afterQueryHandler(RuleDto dto) {
        if (dto.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())){
            if (StrUtil.isNotBlank(dto.getFormalParam()) && StrUtil.isNotBlank(dto.getActualParam())) {
                String[] formalParamSplit = dto.getFormalParam().split(";");
                String[] actualParamSplit = dto.getActualParam().split(";");
                // dto.setFormalParamList(dto.getFormalParam().split(";"));
                // dto.setActualParamList(dto.getActualParam().split(";"));
                List<ParamDto> paramList = new ArrayList<>();
                for (int i = 0; i < formalParamSplit.length; i++) {
                    paramList.add(new ParamDto(formalParamSplit[i], actualParamSplit[i]));
                }
                dto.setParamList(paramList);
            }

        }

        return dto;
    }
}
