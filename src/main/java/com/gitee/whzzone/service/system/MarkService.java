package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.system.DataScopeInfo;
import com.gitee.whzzone.pojo.dto.system.MarkDto;
import com.gitee.whzzone.pojo.entity.system.Mark;
import com.gitee.whzzone.pojo.entity.system.Rule;
import com.gitee.whzzone.pojo.query.system.MarkQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public interface MarkService extends EntityService<Mark, MarkDto, MarkQuery> {

    Rule getByName(String name);

    DataScopeInfo execRuleByName(String name);

    DataScopeInfo execRuleByEntity(Rule entity);

    boolean existSameName(Long id, String scopeName);

    PageData<MarkDto> page(MarkQuery query);

    void enabledSwitch(Long id);

    List<MarkDto> list(MarkQuery query);

    void removeAllByRoleIdAndMarkId(Long roleId, Long markId);

    boolean addRelation(Long roleId, Long markId, Long ruleId);
}
