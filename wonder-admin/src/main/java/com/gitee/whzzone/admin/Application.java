package com.gitee.whzzone.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.containsProperty("server.servlet.context-path") ? env.getProperty("server.servlet.context-path") : "";
        log.info("=======> http://localhost:" + port + path + "/doc.html <=======");
    }

}
