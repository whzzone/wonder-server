package com.gitee.whzzone.admin.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class EventConfig {

    @Autowired
    private TaskExecutor taskExecutor;

    @Bean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public SimpleApplicationEventMulticaster eventMulticaster(){
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        // 如果没配置taskExecutor，那么将会同步执行
        eventMulticaster.setTaskExecutor(taskExecutor);
        return eventMulticaster;
    }
}