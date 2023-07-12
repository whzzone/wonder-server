package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.ResetPWDDto;
import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.entity.Dept;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;

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
