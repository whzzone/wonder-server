package com.gitee.whzzone.admin.business.controller;

import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Create by whz at 2023/8/4
 */
@Api(tags = "文件相关")
@RestController
@RequestMapping("file")
public class FileController {

    private final String uploadDir = "C:\\Users\\Administrator\\Pictures\\upload";

    @ApiOperation(value = "文件上传", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("upload")
    public Result<?> upload(@RequestPart MultipartFile file) {
        if (file == null) {
            throw new RuntimeException("Please select a file to upload");
        }

        try {
            // 获取文件名并消除空格
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // 构造文件路径
            String filePath = uploadDir + File.separator + fileName;

            // 保存文件
            file.transferTo(new File(filePath));

            return Result.ok("File uploaded successfully");
        } catch (IOException e) {
            return Result.error("Failed to upload file");
        }
    }

}
