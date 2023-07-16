package com.gitee.whzzone.common.base.service.impl;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.common.base.service.EntityService;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<T extends BaseEntity<T>, D extends EntityDto> implements EntityService<T, D> {

}
