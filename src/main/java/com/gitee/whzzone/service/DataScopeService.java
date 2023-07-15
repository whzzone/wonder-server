package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.DataScopeDto;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.DataScope;
import com.gitee.whzzone.pojo.query.DataScopeQuery;

/**
 * @author Create by whz at 2023/7/13
 */
public interface DataScopeService extends EntityService<DataScope, DataScopeDto> {

    DataScope getByName(String name);

    DataScopeInfo execRuleByName(String name);

    DataScopeInfo execRuleByEntity(DataScope entity);

    boolean existSameName(Long id, String scopeName);

    PageData<DataScopeDto> page(DataScopeQuery query);

    void enabledSwitch(Long id);
}
