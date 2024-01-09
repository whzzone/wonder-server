package com.gitee.whzzone.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.annotation.Query;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.dto.EntityDto;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
import com.gitee.whzzone.web.pojo.sort.Sort;
import com.gitee.whzzone.web.queryhandler.BaseQueryHandler;
import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.web.utils.ThenerUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, D extends EntityDto, Q extends EntityQuery> extends ServiceImpl<M, T> implements EntityService<T, D, Q> {

    @Autowired
    private ApplicationContext context;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(D dto) {
        try {
            dto = beforeSaveOrUpdateHandler(dto);
            dto = beforeSaveHandler(dto);

            Class<T> dClass = getTClass();
            T entity = dClass.getDeclaredConstructor().newInstance();

            BeanUtil.copyProperties(dto, entity, getInsertIgnoreField(dto));

            boolean save = save(entity);
            if (!save) {
                throw new RuntimeException("操作失败");
            }
            afterSaveHandler(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T updateById(D dto) {
        try {
            dto = beforeSaveOrUpdateHandler(dto);
            dto = beforeUpdateHandler(dto);

            Class<D> dClass = getDClass();
            Class<? super D> superclass = dClass.getSuperclass();
            Field fieldId = superclass.getDeclaredField("id");
            fieldId.setAccessible(true);
            Integer id = (Integer) fieldId.get(dto);
            T entity = getById(id);
            if (entity == null) {
                throw new RuntimeException(StrUtil.format("【{}】不存在", id));
            }

            BeanUtil.copyProperties(dto, entity, getUpdateIgnoreField(dto));

            boolean b = super.updateById(entity);
            if (!b) {
                throw new RuntimeException("操作失败");
            }
            return afterUpdateHandler(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String[] getInsertIgnoreField(D dto) {
        List<String> list = new ArrayList<>();

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            EntityField webFieldAnnotation = field.getAnnotation(EntityField.class);
            if (webFieldAnnotation == null || !webFieldAnnotation.insert()) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    private String[] getUpdateIgnoreField(D dto) {
        List<String> list = new ArrayList<>();

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            EntityField webFieldAnnotation = field.getAnnotation(EntityField.class);
            if (webFieldAnnotation == null || !webFieldAnnotation.update()) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    private String[] getIgnoreField(D dto, Class<? extends Annotation> annotation) {
        List<String> list = new ArrayList<>();

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(annotation)) {
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

        T entity = getById(id);

        boolean b = SqlHelper.retBool(getBaseMapper().deleteById(id));

        if (b) {
            afterDeleteHandler(entity);
        }
        return b;
    }

    @Override
    public T afterSaveHandler(T entity) {
        return entity;
    }

    @Override
    public T afterUpdateHandler(T entity) {
        return entity;
    }

    @Override
    public D afterQueryHandler(T entity) {
        return BeanUtil.copyProperties(entity, getDClass());
    }

    @Override
    public D afterQueryHandler(T entity, BaseQueryHandler<T, D> queryHandler) {
        try {
            if (queryHandler == null) {
                return afterQueryHandler(entity);
            }
            return queryHandler.apply(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public D afterQueryHandler(T entity, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            BaseQueryHandler<T, D> queryHandler;
            try {
                queryHandler = context.getBean(queryHandlerClass);
            } catch (NoSuchBeanDefinitionException ignored) {
                queryHandler = queryHandlerClass.getDeclaredConstructor().newInstance();
            }
            return afterQueryHandler(entity, queryHandler);
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

            for (T entity : list) {
                D dto = afterQueryHandler(entity, queryHandler);
                dList.add(dto);
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

            for (T entity : list) {
                dList.add(afterQueryHandler(entity, queryHandlerClass));
            }
            return dList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterDeleteHandler(T entity) {

    }

    @Override
    public boolean isExist(Integer id) {
        if (id == null)
            throw new RuntimeException("id 为空");

        long count = count(new QueryWrapper<T>().eq("id", id));
        return count > 0;
    }

    @Override
    public D beforeSaveOrUpdateHandler(D dto) {
        return dto;
    }

    @Override
    public D beforeSaveHandler(D dto) {
        return dto;
    }

    @Override
    public D beforeUpdateHandler(D dto) {
        return dto;
    }

    @Override
    public PageData<D> page(Q query) {
        return page(query, new BaseQueryHandler<T, D>() {
            @Override
            public D apply(T entity) {
                return afterQueryHandler(entity);
            }
        });
    }

    @Override
    public PageData<D> page(Q query, BaseQueryHandler<T, D> queryHandler) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(query);

            IPage<T> page = new Page<>(query.getCurPage(), query.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords(), queryHandler);

            return new PageData<>(dList, page.getTotal(), page.getPages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PageData<D> page(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(query);

            IPage<T> page = new Page<>(query.getCurPage(), query.getPageSize());

            page(page, queryWrapper);

            List<D> dList = afterQueryHandler(page.getRecords(), queryHandlerClass);

            return new PageData<>(dList, page.getTotal(), page.getPages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q query) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(query);
            return afterQueryHandler(list(queryWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q query, BaseQueryHandler<T, D> queryHandler) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(query);
            return afterQueryHandler(list(queryWrapper), queryHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<D> list(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            QueryWrapper<T> queryWrapper = handleQueryWrapper(query);
            return afterQueryHandler(list(queryWrapper), queryHandlerClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q query, QueryWrapper<T> queryWrapper) {
        try {
            Class<? extends EntityQuery> qClass = query.getClass();

            Field[] fields = qClass.getDeclaredFields();

            if (Objects.isNull(queryWrapper)) {
                queryWrapper = new QueryWrapper<>();
            }

            Map<String, Field[]> betweenFieldMap = new HashMap<>();

            handleSort(queryWrapper, query);

            // 遍历所有字段
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(query);

                // 判断该属性是否存在值
                if (Objects.isNull(value) || String.valueOf(value).equals("null") || value.equals("")) {
                    continue;
                }
                // 判断属性是否为静态变量
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                // 是否存在注解@Query
                Query queryAnnotation = field.getDeclaredAnnotation(Query.class);
                if (queryAnnotation == null) {
                    continue;
                }

                String columnName = StrUtil.isBlank(queryAnnotation.column()) ? StrUtil.toUnderlineCase(field.getName()) : queryAnnotation.column();

                switch (queryAnnotation.expression()) {
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
                        betweenFieldMap.putIfAbsent(columnName, new Field[2]);
                        Field[] tempList = betweenFieldMap.get(columnName);
                        tempList[tempList[0] == null ? 0 : 1] = field;
                        break;
                }
            }

            handleBetween(queryWrapper, betweenFieldMap, query);

            return queryWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q query) {
        return handleQueryWrapper(query,null);
    }

    private void handleSort(QueryWrapper<T> queryWrapper, Q query) {
        List<Sort> sorts = query.getSorts();
        if (CollectionUtil.isEmpty(sorts)) {
            return;
        }

        for (Sort sort : sorts) {
            if (sort.getAsc() == null || sort.getField() == null) {
                continue;
            }

            if (sort.getAsc()) {
                queryWrapper.orderByAsc(StrUtil.toUnderlineCase(sort.getField()));
            } else {
                queryWrapper.orderByDesc(StrUtil.toUnderlineCase(sort.getField()));
            }
        }
    }

    private void handleBetween(QueryWrapper<T> queryWrapper, Map<String, Field[]> betweenFieldMap, Q query) throws IllegalAccessException {
        Set<String> keySet = betweenFieldMap.keySet();
        for (String columnName : keySet) {
            // 已在编译时做了相关校验，在此无须做重复且耗时的校验
            Field[] itemFieldList = betweenFieldMap.get(columnName);
            if (itemFieldList.length != 2) {
                throw new IllegalArgumentException("查询参数数量对应异常");
            }

            Field field1 = itemFieldList[0];
            Field field2 = itemFieldList[1];

            if (field1.get(query) instanceof Date) {
                if (ThenerUtil.compareFields(field1, field2, query)) {
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') >= date_format({0},'%y%m%d')", field1.get(query));
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') <= date_format({0},'%y%m%d')", field2.get(query));
                } else {
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') <= date_format({0},'%y%m%d')", field1.get(query));
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') >= date_format({0},'%y%m%d')", field2.get(query));
                }
            } else {
                //其他类型，数字、字符等等实现了Comparable接口的类型
                if (!ThenerUtil.compareFields(field1, field2, query)) {
                    queryWrapper.between(columnName, field1.get(query), field2.get(query));
                } else {
                    queryWrapper.between(columnName, field2.get(query), field1.get(query));
                }
            }

        }
    }

}
