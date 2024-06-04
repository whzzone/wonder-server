package com.gitee.whzzone.web.queryhandler;

import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.entity.BaseEntity;

import java.util.List;

/**
 * @author Create by whz at 2023/11/8
 */
public interface BaseQueryHandler<T extends BaseEntity, D extends EntityDTO> {

    D process(T entity);

    List<D> process(List<T> entityList);

}
