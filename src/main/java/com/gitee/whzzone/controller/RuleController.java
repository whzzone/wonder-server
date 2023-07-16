package com.gitee.whzzone.controller;

import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.pojo.entity.Rule;
import com.gitee.whzzone.service.RuleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Create by whz at 2023/7/16
 */
@Api(tags = "规则相关")
@RestController
@RequestMapping("rule")
@Slf4j
public class RuleController extends EntityController<Rule, RuleService, RuleDto> {

    @Autowired
    private RuleService ruleService;

}
