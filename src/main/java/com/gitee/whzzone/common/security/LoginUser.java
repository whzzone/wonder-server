package com.gitee.whzzone.common.security;

import com.gitee.whzzone.pojo.entity.User;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User {

    private String token;
}
