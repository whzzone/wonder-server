package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.query.RuleQuery;
import com.gitee.whzzone.admin.system.service.RuleService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Create by whz at 2023/7/16
 */
@Api(tags = "规则相关")
@RestController
@RequestMapping("rule")
@Slf4j
public class RuleController extends EntityController<Rule, RuleService, RuleDto, RuleQuery> {

    @Autowired
    private RuleService ruleService;

    @ApiOperation("根据markId获取ruleList")
    @GetMapping("getByMarkId/{markId}")
    public Result<List<RuleDto>> getByMarkId(@PathVariable Long markId){
        return Result.ok(ruleService.getByMarkId(markId));
    }

    @ApiOperation("根据roleId，markId获取一个rule")
    @GetMapping("getByRoleIdAndMarkId")
    public Result<RuleDto> getByRoleIdAndMarkId(Long roleId, Long markId){
        return Result.ok(ruleService.getByRoleIdAndMarkId(roleId, markId));
    }

}
