package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.DeptDto;
import com.gitee.whzzone.pojo.entity.Dept;
import com.gitee.whzzone.pojo.query.DeptQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

public interface DeptService extends EntityService<Dept, DeptDto> {

    List<DeptDto> list(DeptQuery query);

    boolean hasChildren(Long id);

    List<DeptDto> tree(DeptQuery query);

    void enabledSwitch(Long id);

    List<Long> getThisAndChildIds(Long id);

    List<Dept> findInIds(List<Long> deptIds);

    boolean existAll(List<Long> ids);

    List<DeptDto> getDtoListIn(List<Long> deptIds);
}
