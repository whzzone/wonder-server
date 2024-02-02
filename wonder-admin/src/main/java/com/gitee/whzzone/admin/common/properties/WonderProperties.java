package com.gitee.whzzone.admin.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Create by whz at 2023/9/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wonder")
public class WonderProperties {

    private WebProperties web = new WebProperties();

    private UserProperties user = new UserProperties();

    private UploadProperties upload = new UploadProperties();

    @Data
    public static class WebProperties {
        private Boolean enableLog;
        private List<String> ignorePath;
    }

    @Data
    public static class UserProperties {
        private String defaultPassword;
    }

    @Data
    public static class UploadProperties {
        private String dir;
        private List<String> allowType;
    }

}



