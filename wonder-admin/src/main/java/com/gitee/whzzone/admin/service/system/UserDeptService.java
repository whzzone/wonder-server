package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.UserDeptDto;
import com.gitee.whzzone.admin.pojo.entity.system.UserDept;
import com.gitee.whzzone.admin.pojo.query.system.UserDeptQuery;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */
public interface UserDeptService extends EntityService<UserDept, UserDeptDto, UserDeptQuery> {

    void addRelation(Long userId, Long deptId);

    void addRelation(Long userId, List<Long> deptIds);

    void removeRelation(Long userId);

    List<UserDept> getByUserId(Long userId);

}
