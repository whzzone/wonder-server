package com.gitee.whzzone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.mapper.DataScopeMapper;
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

    @Override
    public List<Long> execRule(String name) {
        try {
            DataScope dataScopeEntity = getByName(name);
            if (dataScopeEntity == null)
                throw new RuntimeException("数据规则不存在：" + name);
            
            // todo 需要判断provideType

            Class<?> clazz = Class.forName(dataScopeEntity.getClassName());

            Method targetMethod = clazz.getDeclaredMethod(dataScopeEntity.getMethodName());

            if (Modifier.isStatic(targetMethod.getModifiers())) {
                // 设置静态方法可访问
                targetMethod.setAccessible(true);
                // 执行静态方法
                return (List<Long>) targetMethod.invoke(null);
            } else {
                // 创建类实例
                Object obj = clazz.newInstance();
                // 执行方法
                return (List<Long>) targetMethod.invoke(obj);
            }
        }catch (NoSuchMethodException e){
            throw new RuntimeException("配置了不存在的方法");
        }catch (ClassNotFoundException e){
            throw new RuntimeException("配置了不存在的类");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("其他错误：" + e.getMessage());
        }
    }
}
