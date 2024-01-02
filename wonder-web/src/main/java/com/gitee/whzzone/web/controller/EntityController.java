package com.gitee.whzzone.web.controller;

import com.gitee.whzzone.annotation.ApiLogger;
import com.gitee.whzzone.web.pojo.dto.EntityDto;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.other.Result;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 基础的控制器
 * @author Create by whz at 2023/7/8
 */
public abstract class EntityController<T extends BaseEntity, S extends EntityService<T, D, Q>, D extends EntityDto, Q extends EntityQuery> {

    @Autowired
    private S service;

    @ApiLogger
    @ApiOperation("获取")
    @GetMapping("/get/{id}")
    public Result<D> get(@PathVariable Integer id){
        T entity = service.getById(id);
        return Result.ok("操作成功", service.afterQueryHandler(entity));
    }

    @ApiLogger
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id){
        return Result.ok("操作成功", service.removeById(id));
    }

    @ApiLogger
    @ApiOperation("保存")
    @PostMapping("save")
    public Result<T> save(@Validated(InsertGroup.class) @RequestBody D dto){
        return Result.ok("操作成功", service.save(dto));
    }

    @ApiLogger
    @ApiOperation("更新")
    @PostMapping("update")
    public Result<T> update(@Validated(UpdateGroup.class) @RequestBody D dto){
        return Result.ok("操作成功", service.updateById(dto));
    }

    @ApiLogger
    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<D>> page(@RequestBody Q query){
        return Result.ok("操作成功", service.page(query));
    }

    @ApiLogger
    @ApiOperation("列表")
    @PostMapping("list")
    public Result<List<D>> list(@RequestBody Q query){
        return Result.ok("操作成功", service.list(query));
    }

}
