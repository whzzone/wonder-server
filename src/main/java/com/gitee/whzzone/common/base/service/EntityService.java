package com.gitee.whzzone.common.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.pojo.PageData;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author : whz
 * @date : 2023/5/22 16:27
 */

public interface EntityService<T extends BaseEntity<T>, D extends EntityDto, Q extends EntityQuery> extends IService<T> {
    /**
     * 添加
     * @param d 实体对应的Dto
     * @return 实体
     */
    @Transactional(rollbackFor = Exception.class)
    default T save(D d) {
        try {
            d = beforeSaveHandler(d);

            Class<T> dClass = getTClass();
            T t = dClass.getDeclaredConstructor().newInstance();

            BeanUtil.copyProperties(d, t);
            boolean save = save(t);
            if (!save) {
               throw new RuntimeException("操作失败");
            }
            afterSaveHandler(t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 更新
     * @param d 实体对应的Dto
     * @return 是否更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateById(D d) {
        try {
            d = beforeUpdateHandler(d);

            Class<D> dClass = getDClass();
            Class<? super D> superclass = dClass.getSuperclass();
            Field fieldId = superclass.getDeclaredField("id");
            fieldId.setAccessible(true);
            long id = (long) fieldId.get(d);
            T t = getById(id);
            if (t == null) {
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            BeanUtil.copyProperties(d, t);
            boolean b = IService.super.updateById(t);
            if (b) {
                afterUpdateHandler(t);
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    default T getById(Serializable id) {
        if (id == null)
            return null;
        return IService.super.getById(id);
    }

    /*default D getDtoById(Serializable id) {
        try {
            T t = IService.super.getById(id);
            if (t == null) {
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            Class<D> dClass = getDClass();

            D d = dClass.newInstance();
            BeanUtil.copyProperties(t, d);

            return afterQueryHandler(d);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }*/

    @Override
    default boolean removeById(T entity) {
        return removeById(entity.getId());
    }

    @Override
    default boolean removeById(Serializable id) {
        if (id == null){
            throw new RuntimeException("id不能为空");
        }
        T t = getById(id);
        boolean b = IService.super.removeById(id);
        if (b) {
            afterDeleteHandler(t);
        }
        return b;
    }

    default T afterSaveHandler(T t) {
        return t;
    }

    default T afterUpdateHandler(T t) {
        return t;
    }

    /*default D afterQueryHandler(D d) {
        return d;
    }*/

    default D afterQueryHandler(T t) {
        Class<D> dClass = getDClass();
        return BeanUtil.copyProperties(t, dClass);
    }

    default List<D> afterQueryHandler(List<T> list) {
        List<D> dList = new LinkedList<>();
        for (T t : list) {
            D d = afterQueryHandler(t);
            dList.add(d);
        }
        return dList;
    }

    /*default List<D> afterQueryHandler(List<D> list) {
        for (D d : list) {
            afterQueryHandler(d);
        }
        return list;
    }*/

    default void afterDeleteHandler(T t) {

    }

    default Class<T> getTClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 0);
    }

    default Class<D> getDClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 1);
    }

    default Class<Q> getQClass() {
        return (Class<Q>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityService.class, 2);
    }

    default boolean isExist(Long id) {
        if (id == null)
            throw new RuntimeException("id 为空");

        long count = count(new QueryWrapper<T>().eq("id", id));
        return count > 0;
    }

    default D beforeSaveHandler(D d) {
        return d;
    }

    default D beforeUpdateHandler(D d) {
        return d;
    }

    default PageData<D> page(Q q){
        try {
            Class<? extends EntityQuery> qClass = q.getClass();

            Field[] fields = qClass.getDeclaredFields();

            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            for (Field field : fields) {
                if (isBusinessField(field.getName())){
                    field.setAccessible(true);
                    queryWrapper.eq(
                            Objects.nonNull(field.get(q))
                                    && !String.valueOf(field.get(q)).equals("null")
                                    && !field.get(q).equals(""),
                            StrUtil.toUnderlineCase(field.getName()),
                            field.get(q));
                }
            }

            IPage<T> page = new Page<>(q.getCurPage(), q.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords());

            return new PageData<>(dList, page.getTotal(), page.getPages());

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /*default List<D> list(Q q){
        try {
            Class<? extends EntityQuery> qClass = q.getClass();

            Field[] fields = qClass.getDeclaredFields();

            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            for (Field field : fields) {
                if (isBusinessField(field.getName())){
                    field.setAccessible(true);
                    queryWrapper.eq(
                            Objects.nonNull(field.get(q))
                                    && !String.valueOf(field.get(q)).equals("null")
                                    && !field.get(q).equals(""),
                            StrUtil.toUnderlineCase(field.getName()),
                            field.get(q));
                }
            }

            List<T> list = list(queryWrapper);
            return afterQueryHandler(list);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }*/

    default boolean isBusinessField(String fieldName){
        String [] notQueryField = new String[] {"id", "createTime", "createBy", "updateTime", "updateBy", "deleted", "curPage", "pageSize"};
        for (String item : notQueryField) {
            if (fieldName.equals(item))
                return false;
        }
        return true;
    }
}
