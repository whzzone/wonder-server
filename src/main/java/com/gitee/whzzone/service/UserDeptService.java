package com.gitee.whzzone.service;

import com.gitee.whzzone.pojo.dto.UserDeptDto;
import com.gitee.whzzone.pojo.entity.UserDept;

import javax.validation.constraints.NotNull;

/**
 * @author Create by whz at 2023/7/9
 */
public interface UserDeptService extends IEntityService<UserDept, UserDeptDto> {

    void addRelation(@NotNull Long userId, @NotNull Long deptId);

    void removeRelation(@NotNull Long userId);
}
