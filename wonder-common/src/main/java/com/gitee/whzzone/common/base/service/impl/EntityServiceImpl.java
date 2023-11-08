package com.gitee.whzzone.common.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.SaveField;
import com.gitee.whzzone.common.annotation.SelectColumn;
import com.gitee.whzzone.common.annotation.UpdateField;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.common.base.pojo.sort.Sort;
import com.gitee.whzzone.common.base.queryhandler.BaseQueryHandler;
import com.gitee.whzzone.common.base.service.EntityService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, D extends EntityDto, Q extends EntityQuery> extends ServiceImpl<M, T> implements EntityService<T, D, Q> {

    @Autowired
    private ApplicationContext context;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(D d) {
        try {
            d = beforeSaveOrUpdateHandler(d);
            d = beforeSaveHandler(d);

            Class<T> dClass = getTClass();
            T t = dClass.getDeclaredConstructor().newInstance();

            BeanUtil.copyProperties(d, t, getIgnoreSaveField(d));
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
            Integer id = (Integer) fieldId.get(d);
            T t = getById(id);
            if (t == null) {
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            BeanUtil.copyProperties(d, t, getIgnoreUpdateField(d));
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


    private String[] getIgnoreSaveField(D d) {

        List<String> list = new ArrayList<>();

        Field[] fields = d.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(SaveField.class)) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    private String[] getIgnoreUpdateField(D d) {

        List<String> list = new ArrayList<>();

        Field[] fields = d.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(UpdateField.class)) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
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
        return BeanUtil.copyProperties(t, getDClass());
    }

    @Override
    public D afterQueryHandler(T t, BaseQueryHandler<T, D> queryHandler) {
        try {
            if (queryHandler == null) {
                return afterQueryHandler(t);
            }
            return queryHandler.apply(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public D afterQueryHandler(T t, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            BaseQueryHandler<T, D> queryHandler;
            try {
                queryHandler = context.getBean(queryHandlerClass);
            } catch (NoSuchBeanDefinitionException ignored) {
                queryHandler = queryHandlerClass.getDeclaredConstructor().newInstance();
            }
            return afterQueryHandler(t, queryHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<D> afterQueryHandler(List<T> list) {
        return afterQueryHandler(list, new BaseQueryHandler<T, D>() {
            @Override
            public D apply(T entity) {
                return afterQueryHandler(entity);
            }
        });
    }

    @Override
    public List<D> afterQueryHandler(List<T> list, BaseQueryHandler<T, D> queryHandler) {
        try {
            List<D> dList = new ArrayList<>();

            if (CollectionUtil.isEmpty(list)) {
                return dList;
            }

            for (T t : list) {
                D d = afterQueryHandler(t, queryHandler);
                dList.add(d);
            }
            return dList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<D> afterQueryHandler(List<T> list, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            List<D> dList = new ArrayList<>();
            if (CollectionUtil.isEmpty(list)) {
                return dList;
            }

            for (T t : list) {
                dList.add(afterQueryHandler(t, queryHandlerClass));
            }
            return dList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
        return page(q, new BaseQueryHandler<T, D>() {
            @Override
            public D apply(T entity) {
                return afterQueryHandler(entity);
            }
        });
    }

    @Override
    public PageData<D> page(Q q, BaseQueryHandler<T, D> queryHandler) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);

            IPage<T> page = new Page<>(q.getCurPage(), q.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords(), queryHandler);

            return new PageData<>(dList, page.getTotal(), page.getPages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PageData<D> page(Q q, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);

            IPage<T> page = new Page<>(q.getCurPage(), q.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords(), queryHandlerClass);

            return new PageData<>(dList, page.getTotal(), page.getPages());

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

    @Override
    public List<D> list(Q q, BaseQueryHandler<T, D> queryHandler) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);
            return afterQueryHandler(list(queryWrapper), queryHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q q, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(q);
            return afterQueryHandler(list(queryWrapper), queryHandlerClass);
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

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q q) {
        try {
            Class<? extends EntityQuery> qClass = q.getClass();

            Field[] fields = qClass.getDeclaredFields();

            QueryWrapper<T> queryWrapper = new QueryWrapper<>();

            Map<String, Field[]> betweenFieldMap = new HashMap<>();

            // 处理@SelectColumn
            handleSelectColumn(queryWrapper, qClass);

            handleSort(queryWrapper, q);

            // 遍历所有字段
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(q);

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

                switch (query.expression()) {
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

            return queryWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void handleSort(QueryWrapper<T> queryWrapper, Q q) {
        List<Sort> sorts = q.getSorts();
        if (CollectionUtil.isEmpty(sorts)) {
            return;
        }

        for (Sort sort : sorts) {
            if (sort.getAsc()) {
                queryWrapper.orderByAsc(StrUtil.toUnderlineCase(sort.getField()));
            } else {
                queryWrapper.orderByDesc(StrUtil.toUnderlineCase(sort.getField()));
            }
        }
    }

}
