package com.gitee.whzzone.admin.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {

    private List<T> list;

    private Long total;

    private Long pages;

    public PageData(IPage<T> page){
        this.list = page.getRecords();
        this.pages = page.getPages();
        this.total = page.getTotal();
    }

}
