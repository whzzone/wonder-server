package com.gitee.whzzone.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
import com.gitee.whzzone.web.queryhandler.BaseQueryHandler;

import java.io.Serializable;
import java.util.List;

/**
 * 基础的服务接口
 * @author : whz
 * @date : 2023/5/22 16:27
 */
public interface EntityService<T extends BaseEntity, D extends EntityDTO, Q extends EntityQuery> extends IService<T> {

    /**
     * 插入
     * @param dto 传输对象
     */
    T add(D dto);

    /**
     * 更新
     * @param dto 传输对象
     */
    T updateById(D dto);

    /**
     * 根据ID查询
     * @param id 主键ID
     */
    @Override
    T getById(Serializable id);

    /**
     * 根据ID查询并使用查询后置处理器处理
     * @param id 主键ID
     * @param queryHandlerClass 后置处理器
     */
    D getById(Serializable id, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    /**
     * 根据实体ID删除
     * @param entity 实体
     */
    @Override
    boolean removeById(T entity);

    /**
     * 根据ID删除
     * @param id 主键ID
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 插入后置方法
     * @param entity 实体
     * @param dto 传输对象
     */
    void afterAddHandler(T entity, D dto);

    /**
     * 更新后置方法
     * @param rawEntity 原始实体
     * @param newEntity 新的实体
     * @param dto 传输对象
     */
    void afterUpdateHandler(T rawEntity, T newEntity, D dto);

    /**
     * 查询后置方法
     * @param entity 实体
     */
    D afterQueryHandler(T entity);

    /**
     * 查询后置方法
     * @param entity 实体
     * @param queryHandlerClass 处理器类
     */
    D afterQueryHandler(T entity, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    /**
     * 实体list查询后置方法
     * @param list 实体list
     */
    List<D> afterQueryHandler(List<T> list);

    /**
     * 实体list查询后置方法
     * @param list 实体list
     * @param queryHandlerClass 处理器类
     */
    List<D> afterQueryHandler(List<T> list, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    /**
     * 删除后置方法
     * @param entity 被删除的实体
     */
    void afterDeleteHandler(T entity);

    /**
     * 是否存在ID记录
     * @param id 主键ID
     */
    boolean isExist(Integer id);

    /**
     * 插入、更新前置方法，优先级大于beforeAddHandler和beforeUpdateHandler
     * @param dto 传输对象
     */
    D beforeAddOrUpdateHandler(D dto);

    /**
     * 插入前置方法
     * @param dto 传输对象
     */
    D beforeAddHandler(D dto);

    /**
     * 更新前置方法
     * @param dto 传输对象
     */
    D beforeUpdateHandler(D dto);

    /**
     * 分页查询
     * @param query 查询参数
     */
    PageData<D> page(Q query);

    /**
     * 分页查询
     * @param query 分页参数
     * @param queryHandlerClass 查询后置处理器类
     */
    PageData<D> page(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    /**
     * 列表查询
     * @param query 查询参数
     */
    List<D> list(Q query);

    /**
     * 列表查询
     * @param query 查询参数
     * @param queryHandlerClass 查询后置处理器类
     */
    List<D> list(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass);

    /**
     * 根据查询参数返回QueryWrapper
     * @param query 查询参数
     * @param queryWrapper QueryWrapper
     */
    QueryWrapper<T> handleQueryWrapper(Q query, QueryWrapper<T> queryWrapper);

    /**
     * 根据查询参数返回QueryWrapper
     * @param query 查询参数
     */
    QueryWrapper<T> handleQueryWrapper(Q query);

    /**
     * 删除前置方法
     * @param entity 被删除的实体
     */
    void beforeDeleteHandler(T entity);
}
