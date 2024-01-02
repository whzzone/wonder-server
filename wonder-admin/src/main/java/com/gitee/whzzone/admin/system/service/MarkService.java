package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Mark;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.MarkDto;
import com.gitee.whzzone.admin.system.pojo.query.MarkQuery;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.EntityService;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public interface MarkService extends EntityService<Mark, MarkDto, MarkQuery> {

    List<Rule> getByName(String name);

    DataScopeInfo execRuleByName(String name);

    boolean existSameName(Integer id, String scopeName);

    PageData<MarkDto> page(MarkQuery query);

    void enabledSwitch(Integer id);

    List<MarkDto> list(MarkQuery query);

    void removeAllByRoleIdAndMarkId(Integer roleId, Integer markId);

    boolean addRelation(Integer roleId, Integer markId, Integer ruleId);
}
