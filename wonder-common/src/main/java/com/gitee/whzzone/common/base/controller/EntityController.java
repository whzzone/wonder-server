package com.gitee.whzzone.common.base.controller;

import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.common.annotation.RequestLogger;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.common.base.pojo.query.EntityQuery;
import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.common.group.CreateGroup;
import com.gitee.whzzone.common.group.UpdateGroup;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

public abstract class EntityController<T extends BaseEntity, S extends EntityService<T, D, Q>, D extends EntityDto, Q extends EntityQuery> {

    @Autowired
    private S service;

    @RequestLogger
    @ApiOperation("获取")
    @GetMapping("/get/{id}")
    public Result<D> get(@PathVariable Long id){
        T t = service.getById(id);
        return Result.ok("操作成功", service.afterQueryHandler(t));
    }

    @RequestLogger
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id){
        return Result.ok("操作成功", service.removeById(id));
    }

    @RequestLogger
    @ApiOperation("保存")
    @PostMapping("save")
    public Result<T> save(@Validated(CreateGroup.class) @RequestBody D d){
        return Result.ok("操作成功", service.save(d));
    }

    @RequestLogger
    @ApiOperation("更新")
    @PostMapping("update")
    public Result<T> update(@Validated(UpdateGroup.class) @RequestBody D d){
        return Result.ok("操作成功", service.updateById(d));
    }

    @RequestLogger
    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<D>> page(@RequestBody Q q){
        return Result.ok("操作成功", service.page(q));
    }

    @RequestLogger
    @ApiOperation("列表")
    @PostMapping("list")
    public Result<List<D>> list(@RequestBody Q q){
        return Result.ok("操作成功", service.list(q));
    }

}
