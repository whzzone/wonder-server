package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDto;
import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.pojo.query.DeptQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

public interface DeptService extends EntityService<Dept, DeptDto, DeptQuery> {

    List<DeptDto> list(DeptQuery query);

    boolean hasChildren(Long id);

    List<DeptDto> tree(DeptQuery query);

    void enabledSwitch(Long id);

    List<Long> getThisAndChildIds(Long id);

    List<Dept> findInIds(List<Long> deptIds);

    boolean existAll(List<Long> ids);

    List<DeptDto> getDtoListIn(List<Long> deptIds);
}
