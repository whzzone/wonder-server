package com.gitee.whzzone.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.mapper.DataScopeMapper;
import com.gitee.whzzone.pojo.dto.DataScopeDto;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.DataScope;
import com.gitee.whzzone.service.DataScopeService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class DataScopeServiceImpl extends ServiceImpl<DataScopeMapper, DataScope> implements DataScopeService {

    @Override
    public DataScope getByName(String name) {
        LambdaQueryWrapper<DataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataScope::getScopeName, name);
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

        // Add more type conversions for other types as needed

        throw new IllegalArgumentException("Cannot convert argument to type " + type.getName());
    }

    @Override
    public DataScopeInfo execRuleByEntity(DataScope entity) {
        return execRuleHandler(entity);
    }

    private DataScopeInfo execRuleHandler(DataScope entity) {
        if (entity == null)
            throw new RuntimeException("数据规则不存在");
        DataScopeDto dto = new DataScopeDto();
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
        DataScope entity = getByName(name);
        return execRuleHandler(entity);
    }

}
