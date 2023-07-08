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
public class IdAndNameVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("名称")
    private String name;
}
