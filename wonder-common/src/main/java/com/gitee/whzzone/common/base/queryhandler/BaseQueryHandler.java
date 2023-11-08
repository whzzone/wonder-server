package com.gitee.whzzone.common.base.queryhandler;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;

/**
 * @author Create by whz at 2023/11/8
 */
public interface BaseQueryHandler<T extends BaseEntity, D extends EntityDto> {

    D apply(T entity);

}
