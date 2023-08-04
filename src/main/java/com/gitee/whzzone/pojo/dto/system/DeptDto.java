package com.gitee.whzzone.pojo.dto.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.LongSerializer;
import com.gitee.whzzone.common.validation.group.CreateGroup;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

@Data
public class DeptDto extends EntityDto {

    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("父级名称")
    private String parentName;

    @NotBlank(message = "名称不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("父级id")
    private Integer sort;

    @ApiModelProperty("存在下级部门")
    private Boolean hasChildren;

    @ApiModelProperty("下级部门")
    private List<DeptDto> children;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
