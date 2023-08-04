package com.gitee.whzzone.pojo.dto.system;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import lombok.Data;

/**
 * @author : whz
 * @date : 2023/5/23 10:03
 */
@Data
public class RoleMenuDto extends EntityDto {

    private Long roleId;

    private Long menuId;

}