package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.PageData;
import com.gitee.whzzone.admin.pojo.dto.system.ResetPWDDto;
import com.gitee.whzzone.admin.pojo.dto.system.UserDto;
import com.gitee.whzzone.admin.pojo.entity.system.Dept;
import com.gitee.whzzone.admin.pojo.entity.system.Role;
import com.gitee.whzzone.admin.pojo.entity.system.User;
import com.gitee.whzzone.admin.pojo.query.system.UserQuery;

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
