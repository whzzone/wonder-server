package com.gitee.whzzone.controller;

import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.pojo.query.RuleQuery;
import com.gitee.whzzone.service.RuleService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Create by whz at 2023/7/15
 */
@RestController
@RequestMapping("rule")
@Api(tags = "数据权限")
@Slf4j
public class RuleController extends EntityController<Rule, RuleService, RuleDto> {

    @Autowired
    private RuleService dataScopeService;

    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<RuleDto>> page(@Validated @RequestBody RuleQuery query){
        return Result.ok(dataScopeService.page(query));
    }


    @ApiOperation("改变启用状态")
    @GetMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Long id) {
        dataScopeService.enabledSwitch(id);
        return Result.ok();
    }

}
