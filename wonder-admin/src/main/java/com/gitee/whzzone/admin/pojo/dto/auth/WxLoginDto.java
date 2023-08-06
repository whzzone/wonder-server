package com.gitee.whzzone.admin.pojo.dto.auth;

import lombok.Data;

/**
 * @author : whz
 * @date : 2023/5/19 16:34
 */
@Data
public class WxLoginDto {

    private String scene;
    private String code;
    private String encryptedData;
    private String errMsg;
    private String iv;
    private String rawData;
    private String signature;
    private UserInfo userInfo;

}

@Data
class UserInfo{
    private String avatarUrl;
    private String city;
    private String country;
    private String gender;
    private String nickName;
}
