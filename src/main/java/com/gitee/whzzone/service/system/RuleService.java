package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.system.RuleDto;
import com.gitee.whzzone.pojo.entity.system.Rule;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
public interface RuleService extends EntityService<Rule, RuleDto> {

    List<RuleDto> getByMarkId(Long markId);

    RuleDto getByRoleIdAndMarkId(Long roleId, Long markId);
}
