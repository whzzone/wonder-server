package com.gitee.whzzone.admin.system.pojo.auth;

import com.gitee.whzzone.admin.system.pojo.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : whz
 * @date : 2023/5/18 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDTO {

    private String token;

    private Long expire;

    private UserDTO userinfo;

}
