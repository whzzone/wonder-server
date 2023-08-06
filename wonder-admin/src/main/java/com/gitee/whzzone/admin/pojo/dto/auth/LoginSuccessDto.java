package com.gitee.whzzone.admin.pojo.dto.auth;

import com.gitee.whzzone.admin.pojo.dto.system.UserDto;
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
public class LoginSuccessDto {

    private String token;

    private Long expire;

    private UserDto userinfo;

}