package com.gitee.whzzone.controller;

import com.gitee.whzzone.common.validation.group.CreateGroup;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import com.gitee.whzzone.pojo.dto.BaseDto;
import com.gitee.whzzone.pojo.entity.BaseEntity;
import com.gitee.whzzone.service.IEntityService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Create by whz at 2023/7/8
 */

public abstract class EntityController<T extends BaseEntity<T>, S extends IEntityService<T, D>, D extends BaseDto<D>> {

    @Autowired
    private S service;

    @ApiOperation("获取")
    @GetMapping("/get/{id}")
    public Result<D> get(@PathVariable Long id){
        return Result.ok(service.getDtoById(id));
    }

    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id){
        return Result.ok(service.removeById(id));
    }

    @ApiOperation("保存")
    @PostMapping("save")
    public Result<T> save(@Validated(CreateGroup.class) @RequestBody D d){
        return Result.ok(service.save(d));
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public Result<Boolean> update(@Validated(UpdateGroup.class) @RequestBody D d){
        return Result.ok(service.updateById(d));
    }

}
