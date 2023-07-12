package com.gitee.whzzone.service;

import com.gitee.whzzone.pojo.dto.DeptDto;
import com.gitee.whzzone.pojo.entity.Dept;
import com.gitee.whzzone.pojo.query.DeptQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

public interface DeptService extends IEntityService<Dept, DeptDto> {

    List<DeptDto> list(DeptQuery query);

    boolean hasChildren(long id);

    List<DeptDto> tree(DeptQuery query);

    void enabledSwitch(Long id);

    List<Long> getThisAndChildIds(Long id);

}
