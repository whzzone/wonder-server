package com.example.securitytest.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.securitytest.pojo.dto.BaseDto;
import com.example.securitytest.pojo.entity.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 16:27
 */

public interface IEntityService<T extends BaseEntity<T>, D extends BaseDto<D>> extends IService<T> {
    default T save(D d) {
        try {
            Class<T> dClass = getTClass();
            T t = dClass.getDeclaredConstructor().newInstance();

            BeanUtil.copyProperties(d, t);
            boolean save = save(t);
            if (save){
                afterSaveHandler(t);
                return t;
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    default boolean updateById(D d) {
        try {
            Class<D> dClass = getDClass();
            Field fieldId = dClass.getDeclaredField("id");
            fieldId.setAccessible(true);
            long id = (long) fieldId.get(d);
            T t = getById(id);
            if (t == null){
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            BeanUtil.copyProperties(d, t);
            boolean b = IService.super.updateById(t);
            if (b){
                afterUpdateHandler(t);
            }
            return b;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    default D getDtoById(Serializable id) {
        try {
            T t = IService.super.getById(id);
            if (t == null){
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            Class<D> dClass = getDClass();

            D d = dClass.newInstance();
            BeanUtil.copyProperties(t, d);

            return afterQueryHandler(d);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    default boolean removeById(T entity) {
        T t = getById(entity.getId());
        boolean b = IService.super.removeById(entity);
        if (b){
            afterDeleteHandler(t);
        }
        return b;
    }

    @Override
    default boolean removeById(Serializable id) {
        T t = getById(id);
        boolean b = IService.super.removeById(id);
        if (b){
            afterDeleteHandler(t);
        }
        return b;
    }

    default T afterSaveHandler(T t){
        return t;
    }

    default T afterUpdateHandler(T t){
        return t;
    }

    default D afterQueryHandler(D d){
        return d;
    }

    default List<D> afterQueryHandler(List<D> list){
        for (D d : list) {
            afterQueryHandler(d);
        }
        return list;
    }

    default void afterDeleteHandler(T t){

    }

    default Class<T> getTClass(){
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), IEntityService.class, 0);
    }

    default Class<D> getDClass(){
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), IEntityService.class, 1);
    }

    default boolean isExist(Long id){
        return getById(id) != null;
    }

}
