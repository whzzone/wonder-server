package com.gitee.whzzone.admin.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Create by whz at 2023/9/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private String defaultPassword;

    private List<String> ignorePath;

    private SecurityTokenProperties token = new SecurityTokenProperties();

    private SecurityLoginTypeProperties loginType = new SecurityLoginTypeProperties();

    @Data
    public class SecurityTokenProperties {

        private String header;

        private Long liveTime;

        private TimeUnit liveUnit;

        private Long refreshTime;

        private TimeUnit refreshUnit;

        private String jwtSecret;
    }

    @Data
    public class SecurityLoginTypeProperties {
        private Boolean email;

        private Boolean username;

        private Boolean phone;
    }

}



