package com.gitee.whzzone.web.controller;

import com.gitee.whzzone.annotation.ApiLogger;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.dto.EntityDto;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.other.Result;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 基础的控制器
 * @author Create by whz at 2023/7/8
 */
public abstract class EntityController<T extends BaseEntity, S extends EntityService<T, D, Q>, D extends EntityDto, Q extends EntityQuery> {

    @SuppressWarnings("all")
    @Autowired
    private S service;

    @ApiLogger
    @ApiOperation("获取")
    @GetMapping("{id}")
    public Result<D> get(@PathVariable Integer id){
        T entity = service.getById(id);
        return Result.ok("操作成功", service.afterQueryHandler(entity));
    }

    @ApiLogger
    @ApiOperation("删除")
    @DeleteMapping("{id}")
    public Result<Boolean> delete(@PathVariable Integer id){
        return Result.ok("操作成功", service.removeById(id));
    }

    @ApiLogger
    @ApiOperation("保存")
    @PostMapping
    public Result<T> save(@Validated(InsertGroup.class) @RequestBody D dto){
        return Result.ok("操作成功", service.save(dto));
    }

    @ApiLogger
    @ApiOperation("更新")
    @PutMapping
    public Result<T> update(@Validated(UpdateGroup.class) @RequestBody D dto){
        return Result.ok("操作成功", service.updateById(dto));
    }

    @ApiLogger
    @ApiOperation("分页")
    @GetMapping("page")
    public Result<PageData<D>> page(Q query){
        return Result.ok("操作成功", service.page(query));
    }

    @ApiLogger
    @ApiOperation("列表")
    @GetMapping("list")
    public Result<List<D>> list(@RequestBody Q query){
        return Result.ok("操作成功", service.list(query));
    }

    //-------------------------------------------------------------------------------//
    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // 适配：Long转Date
                if (StringUtils.isNotEmpty(text) && isInteger(text)) {
                    setValue(new Date(Long.parseLong(text)));
                    return;
                }
                setValue(parseDate(text));
            }
        });
    }

    /**
     * 正则匹配是否正整数
     */
    private static boolean isInteger(String str) {
        final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 日期型字符串转化为日期格式
     */
    private static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

}
