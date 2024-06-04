package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Mark;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.MarkDTO;
import com.gitee.whzzone.admin.system.pojo.query.MarkQuery;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.EntityService;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public interface MarkService extends EntityService<Mark, MarkDTO, MarkQuery> {

    List<Rule> getByName(String name);

    DataScopeInfo execRuleByName(String name);

    boolean existSameName(Integer id, String scopeName);

    PageData<MarkDTO> page(MarkQuery query);

    void enabledSwitch(Integer id);

    List<MarkDTO> list(MarkQuery query);

    void removeAllByRoleIdAndMarkId(Integer roleId, Integer markId);

    boolean addRelation(Integer roleId, Integer markId, Integer ruleId);

    void removeRelation(Integer roleId, Integer ruleId);
}
