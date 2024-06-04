package com.gitee.whzzone.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.annotation.Query;
import com.gitee.whzzone.common.util.CommonUtil;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, D extends EntityDTO,
        Q extends EntityQuery> extends ServiceImpl<M, T> implements EntityService<T, D, Q> {

    @Autowired
    private ApplicationContext context;

    private final Class<T> currentEntityClass =
            (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityServiceImpl.class, 1);

    private final Class<D> currentDtoClass =
            (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityServiceImpl.class, 2);

    private final Class<Q> currentQueryClass =
            (Class<Q>) ReflectionKit.getSuperClassGenericType(this.getClass(), EntityServiceImpl.class, 3);

    private final Field[] currentEntityFields = CommonUtil.getAllFieldsIncludingParents(currentEntityClass);

    private final Field[] currentDtoFields = CommonUtil.getAllFieldsIncludingParents(currentDtoClass);

    private final String[] insertIgnoreField = initInsertIgnoreField();

    private final String[] updateIgnoreField = initUpdateIgnoreField();

    private final List<Field> currentQueryFields =
            Arrays.stream(CommonUtil.getAllFieldsIncludingParents(currentQueryClass))
                    .filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList());

    private final Map<String, Query> queryFieldMap = initQueryFieldMap();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T add(D dto) {
        try {
            dto = beforeAddOrUpdateHandler(dto);
            dto = beforeAddHandler(dto);

            T entity = currentEntityClass.getDeclaredConstructor().newInstance();

            BeanUtil.copyProperties(dto, entity, insertIgnoreField);

            boolean save = save(entity);
            if (!save) {
                throw new RuntimeException("操作失败");
            }
            afterAddHandler(entity, dto);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T updateById(D dto) {
        dto = beforeAddOrUpdateHandler(dto);
        dto = beforeUpdateHandler(dto);

        Integer id = dto.getId();
        T entity = getById(id);
        if (entity == null) {
            throw new RuntimeException(StrUtil.format("【{}】不存在", id));
        }

        // 使用Jackson序列化进行深拷贝
        ObjectMapper objectMapper = new ObjectMapper();

        T rawEntity = null;
        try {
            rawEntity = objectMapper.readValue(objectMapper.writeValueAsString(entity), currentEntityClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("深拷贝异常：" + e);
        }

        BeanUtil.copyProperties(dto, entity, updateIgnoreField);

        boolean b = super.updateById(entity);
        if (!b) {
            throw new RuntimeException("操作失败");
        }

        afterUpdateHandler(rawEntity, entity, dto);
        return entity;
    }

    @Override
    public T getById(Serializable id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id不能为空");
        }
        return super.getById(id);
    }

    @Override
    public D getById(Serializable id, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        return afterQueryHandler(getById(id), queryHandlerClass);
    }

    @Override
    public boolean removeById(T entity) {
        return removeById(entity.getId());
    }

    @Override
    public boolean removeById(Serializable id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id不能为空");
        }

        T entity = getById(id);

        if (Objects.isNull(entity)) {
            throw new RuntimeException("记录不存在，删除失败");
        }

        beforeDeleteHandler(entity);

        boolean b = SqlHelper.retBool(getBaseMapper().deleteById(id));

        if (b) {
            afterDeleteHandler(entity);
        }
        return b;
    }

    @Override
    public void afterAddHandler(T entity, D dto) {

    }

    @Override
    public void afterUpdateHandler(T rawEntity, T newEntity, D dto) {

    }

    @Override
    public D afterQueryHandler(T entity) {
        return afterQueryHandler(entity, null);
    }

    @Override
    public D afterQueryHandler(T entity, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        try {
            if (queryHandlerClass == null) {
                return BeanUtil.copyProperties(entity, currentDtoClass);
            }

            BaseQueryHandler<T, D> queryHandler;
            try {
                queryHandler = context.getBean(queryHandlerClass);
            } catch (NoSuchBeanDefinitionException ignored) {
                queryHandler = queryHandlerClass.getDeclaredConstructor().newInstance();
            }
            return queryHandler.process(entity);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<D> afterQueryHandler(List<T> list) {
        return afterQueryHandler(list, null);
    }

    @Override
    public List<D> afterQueryHandler(List<T> list, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        // 当处理器为空，
        if (queryHandlerClass == null) {
            if (CollectionUtil.isEmpty(list)) {
                return Collections.emptyList();
            }

            List<D> dList = new ArrayList<>();
            for (T entity : list) {
                dList.add(afterQueryHandler(entity));
            }

            return dList;
        }

        try {
            // 当处理器不为空
            BaseQueryHandler<T, D> queryHandler;
            try {
                queryHandler = context.getBean(queryHandlerClass);
            } catch (Exception ignored) {
                queryHandler = queryHandlerClass.getDeclaredConstructor().newInstance();
            }
            return queryHandler.process(list);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterDeleteHandler(T entity) {

    }

    @Override
    public boolean isExist(Integer id) {
        if (Objects.isNull(id))
            throw new RuntimeException("id 为空");

        long count = count(new QueryWrapper<T>().eq("id", id));
        return count > 0;
    }

    @Override
    public D beforeAddOrUpdateHandler(D dto) {
        return dto;
    }

    @Override
    public D beforeAddHandler(D dto) {
        return dto;
    }

    @Override
    public D beforeUpdateHandler(D dto) {
        return dto;
    }

    @Override
    public PageData<D> page(Q query) {
        return page(query, null);
    }

    @Override
    public PageData<D> page(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        QueryWrapper<T> queryWrapper = handleQueryWrapper(query);

        IPage<T> page = new Page<>(query.getCurPage(), query.getPageSize());

        page(page, queryWrapper);

        List<D> dList = afterQueryHandler(page.getRecords(), queryHandlerClass);

        return PageData.data(dList, page.getTotal(), page.getPages());
    }

    @Override
    public List<D> list(Q query) {
        QueryWrapper<T> queryWrapper = handleQueryWrapper(query);
        return afterQueryHandler(list(queryWrapper));
    }

    @Override
    public List<D> list(Q query, Class<? extends BaseQueryHandler<T, D>> queryHandlerClass) {
        QueryWrapper<T> queryWrapper = handleQueryWrapper(query);
        return afterQueryHandler(list(queryWrapper), queryHandlerClass);
    }

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q query, QueryWrapper<T> queryWrapper) {
        try {
            if (Objects.isNull(queryWrapper)) {
                queryWrapper = new QueryWrapper<>();
            }

            Map<String, Field[]> betweenFieldMap = new HashMap<>();

            handleSort(queryWrapper, query);

            // 遍历所有字段
            for (Field field : currentQueryFields) {
                field.setAccessible(true);
                Object value = field.get(query);

                // 判断该属性是否存在值
                if (Objects.isNull(value) || String.valueOf(value).equals("null") || value.equals("")) {
                    continue;
                }
                // 设置了 @QueryIgnore 注解，直接忽略
//                QueryIgnore queryIgnore = field.getAnnotation(QueryIgnore.class);
//                if (null != queryIgnore) {
//                    continue;
//                }

                // 是否存在注解@Query，不存在跳过
                Query queryAnnotation = queryFieldMap.get(field.getName());
                if (queryAnnotation == null) {
                    continue;
                }

                String columnName = StrUtil.isBlank(queryAnnotation.column()) ? StrUtil.toUnderlineCase(field.getName())
                        : queryAnnotation.column();

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
                    case RIGHT_LIKE:
                        queryWrapper.likeRight(columnName, value);
                        break;
                    case LEFT_LIKE:
                        queryWrapper.likeLeft(columnName, value);
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
                        if (value.getClass().isArray()) {
                            Object[] array = object2Array(value);
                            if (array.length != 0) {
                                queryWrapper.in(columnName, array);
                            }
                        } else if (value instanceof Collection) {
                            Collection<?> collection = (Collection<?>) value;
                            if (!collection.isEmpty()) {
                                queryWrapper.in(columnName, collection);
                            }
                        } else {
                            queryWrapper.in(columnName, value);
                        }
                        break;
                    case NOT_IN:
                        if (value.getClass().isArray()) {
                            Object[] array = object2Array(value);
                            if (array.length != 0) {
                                queryWrapper.notIn(columnName, array);
                            }
                        } else if (value instanceof Collection) {
                            Collection<?> collection = (Collection<?>) value;
                            if (!collection.isEmpty()) {
                                queryWrapper.notIn(columnName, collection);
                            }
                        } else {
                            queryWrapper.notIn(columnName, value);
                        }
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

    private Object[] object2Array(Object value) {
        Object[] array;
        Class<?> componentType = value.getClass().getComponentType();
        if (componentType.isPrimitive()) {
            int length = Array.getLength(value);
            array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = Array.get(value, i);
            }
        } else {
            array = (Object[]) value;
        }
        return array;
    }

    @Override
    public QueryWrapper<T> handleQueryWrapper(Q query) {
        return handleQueryWrapper(query, null);
    }

    private void handleSort(QueryWrapper<T> queryWrapper, Q query) {
        List<Sort> sorts = query.getSorts();
        if (CollectionUtil.isEmpty(sorts)) {
            return;
        }

        for (Sort sort : sorts) {
            if (Objects.isNull(sort.getAsc()) || Objects.isNull(sort.getField())) {
                continue;
            }

            String sortFieldName = sort.getField();
            Optional<Field> optional =
                    Arrays.stream(currentEntityFields).filter(field -> sortFieldName.equals(field.getName())).findFirst();

            if (!optional.isPresent()) {
                throw new RuntimeException("无效的排序字段：" + sortFieldName);
            }

            if (sort.getAsc()) {
                queryWrapper.orderByAsc(StrUtil.toUnderlineCase(sort.getField()));
            } else {
                queryWrapper.orderByDesc(StrUtil.toUnderlineCase(sort.getField()));
            }
        }
    }

    private void handleBetween(QueryWrapper<T> queryWrapper, Map<String, Field[]> betweenFieldMap, Q query)
            throws IllegalAccessException {
        Set<String> keySet = betweenFieldMap.keySet();
        for (String columnName : keySet) {
            // 已在编译时做了相关校验，在此无须做重复且耗时的校验
            Field[] itemFieldList = betweenFieldMap.get(columnName);
            if (itemFieldList.length != 2) {
                throw new IllegalArgumentException("查询参数数量对应异常");
            }

            Field field1 = itemFieldList[0];
            Field field2 = itemFieldList[1];

            if (Objects.isNull(field1) || Objects.isNull(field2)) {
                continue;
            }

            if (field1.get(query) instanceof Date || field1.get(query) instanceof LocalDateTime || field1.get(query) instanceof LocalDate) {
                if (ThenerUtil.compareFields(field1, field2, query)) {
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') >= date_format({0},'%y%m%d')",
                            field1.get(query));
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') <= date_format({0},'%y%m%d')",
                            field2.get(query));
                } else {
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') <= date_format({0},'%y%m%d')",
                            field1.get(query));
                    queryWrapper.apply("date_format(" + columnName + ",'%y%m%d') >= date_format({0},'%y%m%d')",
                            field2.get(query));
                }
            } else {
                // 其他类型，数字、字符等等实现了Comparable接口的类型
                if (!ThenerUtil.compareFields(field1, field2, query)) {
                    queryWrapper.between(columnName, field1.get(query), field2.get(query));
                } else {
                    queryWrapper.between(columnName, field2.get(query), field1.get(query));
                }
            }

        }
    }

    private String[] initInsertIgnoreField() {
        List<String> list = new ArrayList<>();

        for (Field field : currentDtoFields) {
            EntityField webFieldAnnotation = field.getAnnotation(EntityField.class);
            if (webFieldAnnotation == null || !webFieldAnnotation.addAble()) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    private String[] initUpdateIgnoreField() {
        List<String> list = new ArrayList<>();

        for (Field field : currentDtoFields) {
            EntityField webFieldAnnotation = field.getAnnotation(EntityField.class);
            if (webFieldAnnotation == null || !webFieldAnnotation.updateAble()) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    private Map<String, Query> initQueryFieldMap() {
        Map<String, Query> map = new HashMap<>();
        for (Field item : currentQueryFields) {
            Query annotation = item.getDeclaredAnnotation(Query.class);
            map.put(item.getName(), annotation);
        }
        return map;
    }

    @Override
    public void beforeDeleteHandler(T entity) {

    }
}
