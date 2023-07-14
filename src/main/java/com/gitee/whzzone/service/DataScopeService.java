package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.DataScopeDto;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.DataScope;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public interface DataScopeService extends EntityService<DataScope, DataScopeDto> {

    DataScope getByName(String name);

    /**
     * 执行规则
     * @param name
     */
    List<Long> execRule(String name);

    DataScopeInfo execRuleByName(String name);
}
