package com.gitee.whzzone.web.pojo.query;

import com.gitee.whzzone.web.pojo.other.QueryPage;
import com.gitee.whzzone.web.pojo.sort.Sort;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EntityQuery extends QueryPage {

    @ApiModelProperty("排序")
    private List<Sort> sorts;

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }



}
