package com.gitee.whzzone.web.pojo.other;

import com.gitee.whzzone.annotation.QueryIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Create by whz at 2023/7/6
 */
public class QueryPage {

    @ApiModelProperty(value = "当前页数", required = true)
    @QueryIgnore
    private Integer curPage = 1;

    @ApiModelProperty(value = "每页数量", required = true)
    @QueryIgnore
    private Integer pageSize = 10;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QueryPage{" + "curPage=" + curPage + ", pageSize=" + pageSize + '}';
    }
}
