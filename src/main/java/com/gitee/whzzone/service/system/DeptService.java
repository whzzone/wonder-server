package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.system.DeptDto;
import com.gitee.whzzone.pojo.entity.system.Dept;
import com.gitee.whzzone.pojo.query.system.DeptQuery;

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
