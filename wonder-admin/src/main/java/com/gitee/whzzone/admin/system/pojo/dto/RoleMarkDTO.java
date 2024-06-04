package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/16
 */
@Data
public class RoleMarkDTO extends EntityDTO {

    @EntityField
    private Integer roleId;

    @EntityField
    private Integer markId;

    @EntityField
    private Integer ruleId;

}
