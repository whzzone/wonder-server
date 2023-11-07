package com.gitee.whzzone.common.base.pojo.quey;

import com.gitee.whzzone.common.base.pojo.sort.Sort;
import com.gitee.whzzone.web.QueryPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
@Getter
@Setter
@ToString
public class EntityQuery extends QueryPage {

    @Valid
    @ApiModelProperty("排序")
    private List<Sort> sorts;

}
