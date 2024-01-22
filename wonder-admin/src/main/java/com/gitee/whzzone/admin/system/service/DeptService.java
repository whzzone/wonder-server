package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDTO;
import com.gitee.whzzone.admin.system.pojo.query.DeptQuery;
import com.gitee.whzzone.web.service.EntityService;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

public interface DeptService extends EntityService<Dept, DeptDTO, DeptQuery> {

    List<DeptDTO> list(DeptQuery query);

    boolean hasChildren(Integer id);

    List<DeptDTO> tree(DeptQuery query);

    void enabledSwitch(Integer id);

    List<Integer> getThisAndChildIds(Integer id);

    List<Dept> findInIds(List<Integer> deptIds);

    boolean existAll(List<Integer> ids);

    List<DeptDTO> getDTOListIn(List<Integer> deptIds);
}
