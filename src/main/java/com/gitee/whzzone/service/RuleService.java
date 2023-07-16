package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.pojo.query.RuleQuery;

/**
 * @author Create by whz at 2023/7/13
 */
public interface RuleService extends EntityService<Rule, RuleDto> {

    Rule getByName(String name);

    DataScopeInfo execRuleByName(String name);

    DataScopeInfo execRuleByEntity(Rule entity);

    boolean existSameName(Long id, String scopeName);

    PageData<RuleDto> page(RuleQuery query);

    void enabledSwitch(Long id);
}
