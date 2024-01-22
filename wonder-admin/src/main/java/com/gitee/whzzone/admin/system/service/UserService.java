package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPWDDTO;
import com.gitee.whzzone.admin.system.pojo.dto.UserDTO;
import com.gitee.whzzone.admin.system.pojo.query.UserQuery;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.service.EntityService;

import java.util.List;

/**
 * @author :whz
 * @date : 2023/5/16 23:03
 */
public interface UserService extends EntityService<User, UserDTO, UserQuery> {
    User getByEmail(String email);

    void beforeLoginCheck(User sysUser);

    User getByUsername(String username);

    User getByOpenid(String openid);

    List<Dept> getUserDeptInfo(Integer userId);

    Role getUserRoleInfo(Integer userId);

    PageData<UserDTO> page(UserQuery query);

    void enabledSwitch(Integer id);

    boolean existSameUsername(Integer userId, String username);

    boolean existSamePhone(Integer userId, String phone);

    boolean existSameEmail(Integer userId, String email);

    void resetPWD(ResetPWDDTO dto);

    List<Integer> getDeptIds(Integer userId);

    List<Integer> getRoleIds(Integer userId);

    LoginUser getLoginUserInfo(Integer id);

    LoginUser getLoginUserInfo(String username);

    void asyncUpdateCacheUserInfo();
}
