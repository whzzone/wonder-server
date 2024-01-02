package com.gitee.whzzone.web.pojo.sort;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Create by whz at 2023/10/12
 */
public class Sort {

    @NotBlank(message = "字段名不能为空")
    @ApiModelProperty("字段名")
    private String field;

    @NotNull(message = "是否升序不能为空")
    @ApiModelProperty("是否升序")
    private Boolean asc;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "field='" + field + '\'' +
                ", asc=" + asc +
                '}';
    }

    public Sort(String field, Boolean asc) {
        this.field = field;
        this.asc = asc;
    }

    public Sort() {}
}
