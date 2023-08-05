package com.gitee.whzzone.admin.common.base.service.impl;

import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.admin.common.base.service.EntityService;

/**
 * @author Create by whz at 2023/7/16
 */
public abstract class EntityServiceImpl<T extends BaseEntity<T>, D extends EntityDto, Q extends EntityQuery> implements EntityService<T, D, Q> {

}
