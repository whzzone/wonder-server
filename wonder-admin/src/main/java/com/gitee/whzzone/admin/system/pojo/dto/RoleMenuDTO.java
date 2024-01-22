package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import lombok.Data;

/**
 * @author : whz
 * @date : 2023/5/23 10:03
 */
@Data
public class RoleMenuDTO extends EntityDTO {

    @EntityField
    private Integer roleId;

    @EntityField
    private Integer menuId;

}