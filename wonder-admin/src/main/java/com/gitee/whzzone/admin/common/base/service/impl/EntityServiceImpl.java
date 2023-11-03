package com.gitee.whzzone.admin.common.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.gitee.whzzone.admin.common.PageData;
import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.QueryOrder;
import com.gitee.whzzone.common.annotation.QuerySort;
import com.gitee.whzzone.common.annotation.SelectColumn;
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
            d = beforeSaveOrUpdateHandler(d);
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
            d = beforeSaveOrUpdateHandler(d);
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

        boolean b = SqlHelper.retBool(getBaseMapper().deleteById(id));

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
    public D beforeSaveOrUpdateHandler(D d) {
        return d;
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
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);

            IPage<T> page = new Page<>(q.getCurPage(), q.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords());

            return new PageData<>(dList, page.getTotal(), page.getPages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void handleSelectColumn(QueryWrapper<T> queryWrapper, Class<? extends EntityQuery> qclass) {
        SelectColumn selectColumn = qclass.getAnnotation(SelectColumn.class);
        if (queryWrapper == null || selectColumn == null)
            return;

        if (selectColumn.value() != null && selectColumn.value().length > 0) {
            String[] strings = selectColumn.value();
            for (int i = 0; i < strings.length; i++) {
                strings[i] = StrUtil.toUnderlineCase(strings[i]);
            }
            queryWrapper.select(strings);
        }
    }

    private String handleQuerySort(Field field, Object value) {
        QuerySort querySort = field.getDeclaredAnnotation(QuerySort.class);
        String paramValue = (String) value;
        return paramValue.isEmpty() ? querySort.value() : paramValue;
    }

    private String handleQueryOrder(Field field, Object value) {
        QueryOrder queryOrder = field.getDeclaredAnnotation(QueryOrder.class);
        String paramValue = (String) value;
        return paramValue.isEmpty() ? queryOrder.value() : paramValue;
    }

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q q) {
        try {
            Class<? extends EntityQuery> qClass = q.getClass();

            Field[] fields = qClass.getDeclaredFields();

            QueryWrapper<T> queryWrapper = new QueryWrapper<>();

            Map<String, Field[]> betweenFieldMap = new HashMap<>();

            // 处理@SelectColumn
            handleSelectColumn(queryWrapper, qClass);

            String sortColumn = ""; // 排序字段
            String sortOrder = ""; // 排序方式

            // 遍历所有字段
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(q);

                // 解析排序字段
                if (field.isAnnotationPresent(QuerySort.class)) {
                    sortColumn = handleQuerySort(field, value);
                }
                // 解析排序方式
                if (field.isAnnotationPresent(QueryOrder.class)) {
                    sortOrder = handleQueryOrder(field, value);
                }

                // 判断该属性是否存在值
                if (Objects.isNull(value) || String.valueOf(value).equals("null") || value.equals("")) {
                    continue;
                }

                // 是否存在注解@Query
                Query query = field.getDeclaredAnnotation(Query.class);
                if (query == null) {
                    continue;
                }

                String columnName = StrUtil.isBlank(query.column()) ? StrUtil.toUnderlineCase(field.getName()) : query.column();

                switch (query.expression()){
                    case EQ:
                        queryWrapper.eq(columnName, value);
                        break;
                    case NE:
                        queryWrapper.ne(columnName, value);
                        break;
                    case LIKE:
                        queryWrapper.like(columnName, value);
                        break;
                    case GT:
                        queryWrapper.gt(columnName, value);
                        break;
                    case GE:
                        queryWrapper.ge(columnName, value);
                        break;
                    case LE:
                        queryWrapper.le(columnName, value);
                        break;
                    case LT:
                        queryWrapper.lt(columnName, value);
                        break;
                    case IN:
                        queryWrapper.in(columnName, value);
                        break;
                    case NOT_IN:
                        queryWrapper.notIn(columnName, value);
                        break;
                    case IS_NULL:
                        queryWrapper.isNull(columnName);
                        break;
                    case NOT_NULL:
                        queryWrapper.isNotNull(columnName);
                        break;
                    case BETWEEN:
                        if (betweenFieldMap.containsKey(columnName)) {
                            Field[] f = betweenFieldMap.get(columnName);
                            Field[] tempList = new Field[2];
                            tempList[0] = f[0];
                            tempList[1] = field;
                            betweenFieldMap.put(columnName, tempList);
                        } else {
                            betweenFieldMap.put(columnName, new Field[]{field});
                        }
                        break;
                }
            }

            Set<String> keySet = betweenFieldMap.keySet();
            for (String key : keySet) {
                // 已在编译时做了相关校验，在此无须做重复且耗时的校验
                Field[] itemFieldList = betweenFieldMap.get(key);
                if (itemFieldList.length != 2) {
                    throw new IllegalArgumentException("查询参数数量对应异常");
                }

                Field field1 = itemFieldList[0];
                Field field2 = itemFieldList[1];

                Query query1 = field1.getDeclaredAnnotation(Query.class);

                if (field1.get(q) instanceof Date) {
                    if (query1.left()) {
                        queryWrapper.apply("date_format(" + key + ",'%y%m%d') >= date_format({0},'%y%m%d')", field1.get(q));
                        queryWrapper.apply("date_format(" + key + ",'%y%m%d') <= date_format({0},'%y%m%d')", field2.get(q));
                    } else {
                        queryWrapper.apply("date_format(" + key + ",'%y%m%d') <= date_format({0},'%y%m%d')", field1.get(q));
                        queryWrapper.apply("date_format(" + key + ",'%y%m%d') >= date_format({0},'%y%m%d')", field2.get(q));
                    }
                } else {
                    if (query1.left()) {
                        queryWrapper.between(key, field1.get(q), field2.get(q));
                    } else {
                        queryWrapper.between(key, field2.get(q), field1.get(q));
                    }
                }
            }

            if (sortOrder.equalsIgnoreCase("desc")) {
                queryWrapper.orderByDesc(StrUtil.isNotBlank(sortColumn), StrUtil.toUnderlineCase(sortColumn));
            } else {
                queryWrapper.orderByAsc(StrUtil.isNotBlank(sortColumn), StrUtil.toUnderlineCase(sortColumn));
            }

            return queryWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q q) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);
            return afterQueryHandler(list(queryWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
