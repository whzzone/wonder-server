package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import com.gitee.whzzone.admin.system.entity.Rule;
import com.gitee.whzzone.admin.system.pojo.query.RuleQuery;
import com.gitee.whzzone.admin.system.service.RuleService;
import com.gitee.whzzone.web.pojo.other.Result;
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
public class RuleController extends EntityController<Rule, RuleService, RuleDTO, RuleQuery> {

    @Autowired
    private RuleService ruleService;

    @ApiOperation("根据markId获取ruleList")
    @GetMapping("getByMarkId/{markId}")
    public Result<List<RuleDTO>> getByMarkId(@PathVariable Integer markId){
        return Result.ok(ruleService.getByMarkId(markId));
    }

    @GetMapping("ruleIdsByRoleIdAndMarkId")
    public Result<List<Integer>> getRuleIdsByRoleIdAndMarkId(Integer roleId, Integer markId){
        return Result.ok(ruleService.getRuleIdsByRoleIdAndMarkId(roleId, markId));
    }

}
