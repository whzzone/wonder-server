package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.UserDeptDto;
import com.example.securitytest.pojo.entity.UserDept;

import javax.validation.constraints.NotNull;

/**
 * @author Create by whz at 2023/7/9
 */
public interface UserDeptService extends IEntityService<UserDept, UserDeptDto> {

    void addRelation(@NotNull Long userId, @NotNull Long deptId);

    void removeRelation(@NotNull Long userId);
}
