package com.gitee.whzzone.service;

import com.gitee.whzzone.pojo.dto.PageData;
import com.gitee.whzzone.pojo.dto.ResetPWDDto;
import com.gitee.whzzone.pojo.dto.UserDto;
import com.gitee.whzzone.pojo.entity.Dept;
import com.gitee.whzzone.pojo.entity.Role;
import com.gitee.whzzone.pojo.entity.User;
import com.gitee.whzzone.pojo.query.UserQuery;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface UserService extends IEntityService<User, UserDto> {
    User getByEmail(String email);

    void beforeLoginCheck(User sysUser);

    User getByUsername(String username);

    User getByOpenid(String openid);

    Dept getUserDeptInfo(Long userId);

    Role getUserRoleInfo(Long userId);

    PageData<UserDto> page(UserQuery query);

    void enabledSwitch(Long id);

    boolean existSameUsername(Long userId, String username);

    boolean existSamePhone(Long userId, String phone);

    boolean existSameEmail(Long userId, String email);

    void resetPWD(ResetPWDDto dto);
}
