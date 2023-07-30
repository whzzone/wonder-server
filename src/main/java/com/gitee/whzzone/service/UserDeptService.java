package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.UserDeptDto;
import com.gitee.whzzone.pojo.entity.UserDept;

import java.util.List;

/**
 * @author Create by whz at 2023/7/9
 */
public interface UserDeptService extends EntityService<UserDept, UserDeptDto> {

    void addRelation(Long userId, Long deptId);

    void addRelation(Long userId, List<Long> deptIds);

    void removeRelation(Long userId);

    List<UserDept> getByUserId(Long userId);

}
