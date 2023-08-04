package com.gitee.whzzone.pojo.dto.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.LongSerializer;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/16
 */
@Data
public class RoleMarkDto extends EntityDto {

    @JsonSerialize(using = LongSerializer.class)
    private Long roleId;

    @JsonSerialize(using = LongSerializer.class)
    private Long markId;

    @JsonSerialize(using = LongSerializer.class)
    private Long ruleId;

}
