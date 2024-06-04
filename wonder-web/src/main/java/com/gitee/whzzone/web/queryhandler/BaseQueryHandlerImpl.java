package com.gitee.whzzone.web.queryhandler;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;

import java.util.List;

/**
 * @author Create by whz at 2024/2/28
 */
public abstract class BaseQueryHandlerImpl<T extends BaseEntity, D extends EntityDTO> implements BaseQueryHandler<T, D> {

    // 暂未用到
//    private final Class<T> currentEntityClass =
//            (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseQueryHandlerImpl.class, 0);

    private final Class<D> currentDtoClass =
            (Class<D>)ReflectionKit.getSuperClassGenericType(this.getClass(), BaseQueryHandlerImpl.class, 1);

    @Override
    public D process(T entity) {
        return BeanUtil.copyProperties(entity, currentDtoClass);
    }

    @Override
    public List<D> process(List<T> entityList) {
        return BeanUtil.copyToList(entityList, currentDtoClass);
    }
}
