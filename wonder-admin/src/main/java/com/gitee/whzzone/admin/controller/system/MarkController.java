package com.gitee.whzzone.admin.controller.system;

import com.gitee.whzzone.admin.common.base.controller.EntityController;
import com.gitee.whzzone.admin.pojo.PageData;
import com.gitee.whzzone.admin.pojo.dto.system.MarkDto;
import com.gitee.whzzone.admin.pojo.entity.system.Mark;
import com.gitee.whzzone.admin.pojo.query.system.MarkQuery;
import com.gitee.whzzone.admin.service.system.MarkService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Create by whz at 2023/7/15
 */
@RestController
@RequestMapping("mark")
@Api(tags = "标记相关")
@Slf4j
public class MarkController extends EntityController<Mark, MarkService, MarkDto, MarkQuery> {

    @Autowired
    private MarkService markService;

    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<MarkDto>> page(@Validated @RequestBody MarkQuery query){
        return Result.ok(markService.page(query));
    }

    @ApiOperation("改变启用状态")
    @GetMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Long id) {
        markService.enabledSwitch(id);
        return Result.ok();
    }

    @ApiOperation("列表")
    @PostMapping("list")
    public Result<List<MarkDto>> list(@Validated @RequestBody MarkQuery query){
        return Result.ok("", markService.list(query));
    }

}
