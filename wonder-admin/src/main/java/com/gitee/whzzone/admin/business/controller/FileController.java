package com.gitee.whzzone.admin.business.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Create by whz at 2023/8/4
 */
@Api(tags = "文件相关")
@RestController
@RequestMapping("file")
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @ApiOperation(value = "文件上传", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("upload")
    public Result<?> upload(@RequestPart MultipartFile file) {
        if (file == null) {
            throw new RuntimeException("Please select a file to upload");
        }

        try {
            // 获取文件名并消除空格
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

            // 获取文件扩展名
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String md5 = DigestUtil.md5Hex(file.getBytes());

            // 构造上传目录路径
            String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            uploadPath = Paths.get(uploadPath, currentDate).toString();

            // 兼容Windows和Linux的文件路径分隔符
            String fileSeparator = System.getProperty("file.separator");
            uploadPath = uploadPath.replace("/", fileSeparator);

            // 新的文件名
            String fileName = md5 + fileExtension;

            // 创建上传目录
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // 构造文件路径
            String filePath = Paths.get(uploadPath, fileName).toString();

            // 保存文件
            file.transferTo(new File(filePath));

            return Result.ok("File uploaded successfully", fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("Failed to upload file");
        }
    }

}
