package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.query.RuleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RuleService extends EntityService<Rule, RuleDto, RuleQuery> {

    List<RuleDto> getByMarkId(Long markId);

    RuleDto getByRoleIdAndMarkId(Long roleId, Long markId);

    List<Rule> getByIds(List<Long> ruleIds);
}
