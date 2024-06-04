package com.gitee.whzzone.admin.common.event;

import com.gitee.whzzone.admin.system.entity.RequestLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author Create by whz at 2024/1/29
 */
public class HttpAccessEvent extends ApplicationEvent {

    public HttpAccessEvent(RequestLog source) {
        super(source);
    }

}
