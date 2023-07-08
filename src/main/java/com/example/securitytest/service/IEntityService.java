package com.example.securitytest.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.securitytest.pojo.dto.BaseDto;
import com.example.securitytest.pojo.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 16:27
 */
public interface IEntityService<T extends BaseEntity<T>, D extends BaseDto> extends IService<T> {
    default boolean save(D d) {
        try {
            Class<T> clazz = getEntityClass();
            T t = clazz.getDeclaredConstructor().newInstance();
            BeanUtil.copyProperties(d, t);
            boolean save = save(t);
            if (save){
                afterSaveHandler(t);
            }
            return save;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    default boolean updateById(D d) {
        try {
            Class<T> clazz = getEntityClass();
            T t = clazz.getDeclaredConstructor().newInstance();
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
            Class<D> clazz = getDtoClass();
            D d = clazz.getDeclaredConstructor().newInstance();
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

    Class<T> getEntityClass();
    Class<D> getDtoClass();

}
