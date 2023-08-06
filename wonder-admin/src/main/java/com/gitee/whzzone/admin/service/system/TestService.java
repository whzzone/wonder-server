package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.pojo.dto.system.DataScopeInfo;

/**
 * @author Create by whz at 2023/7/13
 */
public interface TestService {

    DataScopeInfo injectTest(String name, DataScopeInfo info);

    String injectTest2(String name, String info);

}
