package com.gitee.whzzone.admin.common.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.common.PageData;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.QueryOrder;
import com.gitee.whzzone.common.annotation.QuerySort;
import com.gitee.whzzone.common.annotation.SelectColumn;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<M extends BaseMapper<T>, T extends BaseEntity<T>, D extends EntityDto, Q extends EntityQuery> extends ServiceImpl<M, T> implements EntityService<T, D, Q> {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(D d) {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(D d) {
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
            boolean b = super.updateById(t);
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
    public T getById(Serializable id) {
        if (id == null)
            return null;
        return super.getById(id);
    }

    @Override
    public boolean removeById(T entity) {
        return removeById(entity.getId());
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        T t = getById(id);
        boolean b = super.removeById(id);
        if (b) {
            afterDeleteHandler(t);
        }
        return b;
    }

    @Override
    public T afterSaveHandler(T t) {
        return t;
    }

    @Override
    public T afterUpdateHandler(T t) {
        return t;
    }

    @Override
    public D afterQueryHandler(T t) {
        Class<D> dClass = getDClass();
        return BeanUtil.copyProperties(t, dClass);
    }

    @Override
    public List<D> afterQueryHandler(List<T> list) {
        List<D> dList = new ArrayList<>();

        if (CollectionUtil.isEmpty(list)) {
            return dList;
        }

        for (T t : list) {
            D d = afterQueryHandler(t);
            dList.add(d);
        }
        return dList;
    }

    @Override
    public void afterDeleteHandler(T t) {

    }

    @Override
    public boolean isExist(Long id) {
        if (id == null)
            throw new RuntimeException("id 为空");

        long count = count(new QueryWrapper<T>().eq("id", id));
        return count > 0;
    }

    @Override
    public D beforeSaveHandler(D d) {
        return d;
    }

    @Override
    public D beforeUpdateHandler(D d) {
        return d;
    }

    @Override
    public PageData<D> page(Q q) {
        try {
            QueryWrapper<T> queryWrapper = queryWrapperHandler(q);

            IPage<T> page = new Page<>(q.getCurPage(), q.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords());

            return new PageData<>(dList, page.getTotal(), page.getPages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public QueryWrapper<T> queryWrapperHandler(Q q) {
        try {
            Class<? extends EntityQuery> qClass = q.getClass();

            Field[] fields = qClass.getDeclaredFields();

            QueryWrapper<T> queryWrapper = new QueryWrapper<>();

            Map<String, Field[]> betweenFieldMap = new HashMap<>();

            // 处理@SelectColumn
            SelectColumn selectColumn = qClass.getAnnotation(SelectColumn.class);
            if (selectColumn != null && selectColumn.value() != null && selectColumn.value().length > 0){
                String[] strings = selectColumn.value();
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = StrUtil.toUnderlineCase(strings[i]);
                }
                queryWrapper.select(strings);
            }

            String sortColumn = "";
            String sortOrder = "";

            for (Field field : fields) {
                // if (isBusinessField(field.getName())) {
                field.setAccessible(true);
                Object value = field.get(q);

                // 判断该属性是否存在值
                if (Objects.isNull(value) || String.valueOf(value).equals("null") || value.equals("")) {
                    continue;
                }

                // 是否存在注解@QuerySort
                QuerySort querySort = field.getDeclaredAnnotation(QuerySort.class);
                if (querySort != null) {
                    sortColumn = (String) field.get(q);
                }

                QueryOrder queryOrder = field.getDeclaredAnnotation(QueryOrder.class);
                if (queryOrder != null) {
                    sortOrder = (String) field.get(q);
                }

                // 是否存在注解@Query
                Query query = field.getDeclaredAnnotation(Query.class);
                if (query == null) {
                    continue;
                }

                String columnName = StrUtil.isBlank(query.column()) ? StrUtil.toUnderlineCase(field.getName()) : query.column();

                if (query.expression().equals(ExpressionEnum.EQ)) {
                    queryWrapper.eq(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.NE)) {
                    queryWrapper.ne(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.LIKE)) {
                    queryWrapper.like(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.GT)) {
                    queryWrapper.gt(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.GE)) {
                    queryWrapper.ge(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.LT)) {
                    queryWrapper.lt(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.LE)) {
                    queryWrapper.le(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.IN)) {
                    queryWrapper.in(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.NOT_IN)) {
                    queryWrapper.notIn(columnName, value);
                } else if (query.expression().equals(ExpressionEnum.IS_NULL)) {
                    queryWrapper.isNull(columnName);
                } else if (query.expression().equals(ExpressionEnum.NOT_NULL)) {
                    queryWrapper.isNotNull(columnName);
                } else if (query.expression().equals(ExpressionEnum.BETWEEN)) {
                    if (betweenFieldMap.containsKey(columnName)) {
                        Field[] f = betweenFieldMap.get(columnName);
                        Field[] tempList = new Field[2];
                        tempList[0] = f[0];
                        tempList[1] = field;
                        betweenFieldMap.put(columnName, tempList);
                    } else {
                        betweenFieldMap.put(columnName, new Field[]{field});
                    }
                }

            }
            // }

            Set<String> keySet = betweenFieldMap.keySet();
            for (String key : keySet) {
                // 已在编译时做了相关校验，在此无须做重复且耗时的校验
                Field[] itemFieldList = betweenFieldMap.get(key);

                Field field1 = itemFieldList[0];
                Field field2 = itemFieldList[1];

                Query query1 = field1.getDeclaredAnnotation(Query.class);

                if (query1.left()) {
                    queryWrapper.between(key, field1.get(q), field2.get(q));
                } else {
                    queryWrapper.between(key, field2.get(q), field1.get(q));
                }
            }

            if (sortOrder.equalsIgnoreCase("desc")){
                queryWrapper.orderByDesc(StrUtil.isNotBlank(sortColumn), StrUtil.toUnderlineCase(sortColumn));
            }else {
                queryWrapper.orderByAsc(StrUtil.isNotBlank(sortColumn), StrUtil.toUnderlineCase(sortColumn));
            }

            return queryWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q q){
        try {
            QueryWrapper<T> queryWrapper = queryWrapperHandler(q);
            return afterQueryHandler(list(queryWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
