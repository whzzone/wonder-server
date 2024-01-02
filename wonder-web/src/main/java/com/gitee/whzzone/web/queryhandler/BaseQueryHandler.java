package com.gitee.whzzone.web.queryhandler;

import com.gitee.whzzone.web.pojo.dto.EntityDto;
import com.gitee.whzzone.web.entity.BaseEntity;

/**
 * @author Create by whz at 2023/11/8
 */
public interface BaseQueryHandler<T extends BaseEntity, D extends EntityDto> {

    D apply(T entity);

}
