package com.example.securitytest.pojo.dto;

import lombok.Data;

/**
 * @author : whz
 * @date : 2023/5/23 10:03
 */
@Data
public class RoleMenuDto extends BaseDto<RoleMenuDto> {

    private Long roleId;

    private Long menuId;

}