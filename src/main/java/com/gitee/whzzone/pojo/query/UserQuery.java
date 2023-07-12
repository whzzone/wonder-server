package com.gitee.whzzone.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :whz
 * @date : 2022/12/18 14:32
 */
@Data
public class UserQuery extends QueryPage {

    @ApiModelProperty("姓名")
    private String nickname;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("部门id")
    private Long deptId;

}
