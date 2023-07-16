package com.gitee.whzzone.common.base.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.serializer.LongSerializer;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : whz
 * @date : 2023/5/22 16:35
 */
@Getter
@Setter
@ToString
public class EntityDto {

    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    @ApiModelProperty("id")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
