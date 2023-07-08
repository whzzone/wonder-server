package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface UserService extends IEntityService<User, UserDto> {
    User getByEmail(String email);

    void verifyUser(User sysUser);

    User getByUsername(String username);

    User getByOpenid(String openid);

    PageData<UserDto> page(UserQuery query);

    @Override
    default Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
