package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.common.PageData;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.MarkDto;
import com.gitee.whzzone.admin.system.entity.Mark;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.query.MarkQuery;

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
