package com.example.securitytest.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :whz
 * @date : 2022/12/18 14:32
 */
@Data
public class RoleQuery extends QueryPage {

    @ApiModelProperty("角色名称")
    private String name;


}
