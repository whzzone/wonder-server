package com.example.securitytest.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LabelValueVo {

    @ApiModelProperty("键")
    private String label;

    @ApiModelProperty("值")
    private Object value;
}
