package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.query.RuleQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RuleService extends EntityService<Rule, RuleDTO, RuleQuery> {

    List<RuleDTO> getByMarkId(Integer markId);

    List<Integer> getRuleIdsByRoleIdAndMarkId(Integer roleId, Integer markId);

}
