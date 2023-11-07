package com.gitee.whzzone.admin.system.pojo.other.DictData;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.LongSerializer;
import com.gitee.whzzone.common.group.CreateGroup;
import com.gitee.whzzone.common.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 系统字典数据
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@ApiModel(value = "DictDataDto对象", description = "系统字典数据")
public class DictDataDto extends EntityDto {

    @NotNull(message = "字典id不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty(value = "字典id", required = true)
    private Long dictId;

    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty("父级id")
    private Long parentId;

    @NotBlank(message = "字典标签不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty("字典标签")
    private String dictLabel;

    @NotBlank(message = "字典值不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("列表回显样式")
    private String listClass;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;

}
