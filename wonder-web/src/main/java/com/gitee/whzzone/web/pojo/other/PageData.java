package com.gitee.whzzone.web.pojo.other;

import java.util.List;

public class PageData<T> {

    private List<T> list;

    private Long total;

    private Long pages;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
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

    public PageData(List<T> list, Long total, Long pages) {
        this.list = list;
        this.total = total;
        this.pages = pages;
    }

    public PageData() {}
}
