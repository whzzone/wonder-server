package com.gitee.whzzone.common.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.common.base.pojo.query.EntityQuery;
import com.gitee.whzzone.common.base.queryhandler.BaseQueryHandler;

import java.io.Serializable;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 16:27
 */

public interface EntityService<T extends BaseEntity, D extends EntityDto, Q extends EntityQuery> extends IService<T> {

    T save(D d);

    T updateById(D d);

    @Override
    T getById(Serializable id);

    @Override
    boolean removeById(T entity);

    @Override
    boolean removeById(Serializable id);

    T afterSaveHandler(T t);

    T afterUpdateHandler(T t);

    D afterQueryHandler(T t);

    D afterQueryHandler(T t, BaseQueryHandler<T, D> queryHandler);

    D afterQueryHandler(T t, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    List<D> afterQueryHandler(List<T> list);

    List<D> afterQueryHandler(List<T> list, BaseQueryHandler<T, D> queryHandler);

    List<D> afterQueryHandler(List<T> list, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    void afterDeleteHandler(T t);

    boolean isExist(Long id);

    D beforeSaveOrUpdateHandler(D dto);

    D beforeSaveHandler(D d);

    D beforeUpdateHandler(D d);

    PageData<D> page(Q q);

    PageData<D> page(Q q, BaseQueryHandler<T, D> queryHandler);

    PageData<D> page(Q q, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    List<D> list(Q q);

    List<D> list(Q q, BaseQueryHandler<T, D> queryHandler);

    List<D> list(Q q, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    QueryWrapper<T> handleQueryWrapper(Q q);

    default Class<T> getTClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 0);
    }

    default Class<D> getDClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 1);
    }

    default Class<Q> getQClass() {
        return (Class<Q>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 2);
    }

}
