package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.system.entity.Mark;
import com.gitee.whzzone.admin.system.pojo.dto.MarkDTO;
import com.gitee.whzzone.admin.system.pojo.query.MarkQuery;
import com.gitee.whzzone.admin.system.service.MarkService;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Create by whz at 2023/7/15
 */
@RestController
@RequestMapping("mark")
@Api(tags = "标记相关")
@Slf4j
public class MarkController extends EntityController<Mark, MarkService, MarkDTO, MarkQuery> {

    @Autowired
    private MarkService markService;

    @ApiOperation("分页")
    @GetMapping("page")
    public Result<PageData<MarkDTO>> page(MarkQuery query){
        return Result.ok(markService.page(query));
    }

    @ApiOperation("改变启用状态")
    @PutMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Integer id) {
        markService.enabledSwitch(id);
        return Result.ok();
    }

    @ApiOperation("列表")
    @GetMapping("list")
    public Result<List<MarkDTO>> list(MarkQuery query){
        return Result.ok("", markService.list(query));
    }

}
