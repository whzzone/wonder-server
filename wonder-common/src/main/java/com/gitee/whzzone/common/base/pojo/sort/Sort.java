package com.gitee.whzzone.common.base.pojo.sort;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Create by whz at 2023/10/12
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sort {

    @NotBlank(message = "字段名不能为空")
    @ApiModelProperty("字段名")
    private String field;

    @NotNull(message = "是否升序不能为空")
    @ApiModelProperty("是否升序")
    private Boolean asc;

}
