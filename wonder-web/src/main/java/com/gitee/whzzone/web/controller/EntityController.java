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
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public Result<D> get(@PathVariable Integer id){
        T entity = service.getById(id);
        return Result.ok("操作成功", service.afterQueryHandler(entity));
    }

    @ApiLogger
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
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
    @PutMapping("update")
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

}
