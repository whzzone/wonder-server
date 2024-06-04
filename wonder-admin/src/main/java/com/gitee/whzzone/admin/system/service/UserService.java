package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.ChangePasswdDTO;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPasswdDTO;
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
    void loginCheck(User sysUser);

    User getByUsername(String username);

    User getByOpenid(String openid);

    List<Dept> getUserDeptInfo(Integer userId);

    PageData<UserDTO> page(UserQuery query);

    void enabledSwitch(Integer id);

    boolean existUsername(Integer userId, String username);

    boolean existPhone(Integer userId, String phone);

    void resetPasswd(ResetPasswdDTO dto);

    List<Integer> getDeptIds(Integer userId);

    List<Dept> getDepts(Integer userId);

    List<Integer> getRoleIds(Integer userId);

    List<Role> getRoles(Integer userId);

    LoginUser getLoginUserInfo(Integer id);

    User getByPhone(String phone);

    void changPasswd(ChangePasswdDTO dto);

    boolean setUserPasswd(Integer userId, String rawText);

}
