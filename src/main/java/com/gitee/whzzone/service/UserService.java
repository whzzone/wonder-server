package com.gitee.whzzone.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.ResetPWDDto;
import com.gitee.whzzone.pojo.dto.UserDto;
import com.gitee.whzzone.pojo.entity.Dept;
import com.gitee.whzzone.pojo.entity.Role;
import com.gitee.whzzone.pojo.entity.User;
import com.gitee.whzzone.pojo.query.UserQuery;

import java.util.List;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface UserService extends EntityService<User, UserDto> {
    User getByEmail(String email);

    void beforeLoginCheck(User sysUser);

    User getByUsername(String username);

    User getByOpenid(String openid);

    List<Dept> getUserDeptInfo(Long userId);

    Role getUserRoleInfo(Long userId);

    PageData<UserDto> page(UserQuery query);

    void enabledSwitch(Long id);

    boolean existSameUsername(Long userId, String username);

    boolean existSamePhone(Long userId, String phone);

    boolean existSameEmail(Long userId, String email);

    void resetPWD(ResetPWDDto dto);

    List<Long> getDeptIds(Long userId);

    List<Long> getRoleIds(Long userId);
}
