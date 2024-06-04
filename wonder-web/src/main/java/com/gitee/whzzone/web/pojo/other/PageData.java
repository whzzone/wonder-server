package com.gitee.whzzone.web.pojo.other;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.Collections;

/**
 * 分页数据封装
 */
public class PageData<T> {
    @ApiModelProperty("数据列表")
    private Collection<T> list;

    @ApiModelProperty("数据总数")
    private Long total;

    @ApiModelProperty("总页数")
    private Long pages;

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "list=" + list +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }

    private PageData(Collection<T> list, Long total, Long pages) {
        this.list = list;
        this.total = total;
        this.pages = pages;
    }

    private PageData() {}

    /**
     * 返回空的分页
     * @return 空的分页
     */
    public static <T> PageData<T> emptyData() {
        return new PageData<>(Collections.emptyList(), 0L, 0L);
    }

    /**
     * 分页信息
     * @param list 列表
     * @param total 总数
     * @param pages 总页数
     * @return 分页信息
     */
    public static <T> PageData<T> data(Collection<T> list, long total, long pages) {
        return new PageData<>(list, total, pages);
    }

    /**
     * 分页信息
     * @param page {@link IPage}
     * @return 分页信息
     */
    public static <T> PageData<T> data(IPage<T> page) {
        return new PageData<>(page.getRecords(), page.getTotal(), page.getPages());
    }
}
