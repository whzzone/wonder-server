package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPWDDto;
import com.gitee.whzzone.admin.system.pojo.dto.UserDto;
import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.query.UserQuery;

import java.util.List;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface UserService extends EntityService<User, UserDto, UserQuery> {
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
