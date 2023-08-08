package com.gitee.whzzone.admin.common.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.admin.common.PageData;

import java.io.Serializable;
import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 16:27
 */

public interface EntityService<T extends BaseEntity<T>, D extends EntityDto, Q extends EntityQuery> extends IService<T> {

    T save(D d);

    boolean updateById(D d);

    @Override
    T getById(Serializable id);

    @Override
    boolean removeById(T entity);

    @Override
    boolean removeById(Serializable id);

    T afterSaveHandler(T t);

    T afterUpdateHandler(T t);

    D afterQueryHandler(T t);

    List<D> afterQueryHandler(List<T> list);

    void afterDeleteHandler(T t);

    default Class<T> getTClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 0);
    }

    default Class<D> getDClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 1);
    }

    default Class<Q> getQClass() {
        return (Class<Q>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 2);
    }

    boolean isExist(Long id);

    D beforeSaveHandler(D d);

    D beforeUpdateHandler(D d);

    PageData<D> page(Q q);

    QueryWrapper<T> queryWrapperHandler(Q q);

    List<D> list(Q q);

}
