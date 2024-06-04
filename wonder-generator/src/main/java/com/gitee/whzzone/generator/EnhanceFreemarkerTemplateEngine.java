package com.gitee.whzzone.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

/**
 * 自定义DTO、Query模版引擎
 * @author Create by whz at 2024/2/19
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = this.getPathInfo(OutputFile.other);
        customFile.forEach((key, value) -> {
            String fileName = String.format(otherPath + File.separator + entityName + "%s", key);
            this.outputFile(new File(fileName), objectMap, value, true);
        });
    }
}
