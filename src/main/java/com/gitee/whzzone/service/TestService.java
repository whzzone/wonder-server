package com.gitee.whzzone.service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public interface TestService {
    List<Long> injectTest(String name, List<Long> scopeList);
}
