package com.gitee.whzzone.service.impl;

import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class TestServiceImpl implements TestService {

    @Override
    public DataScopeInfo injectTest(String name, @DataScope("sn2") DataScopeInfo info) {
        System.out.println("info = " + info);
        System.out.println("name = " + name);
        return info;
    }

    @Override
    public String injectTest2(String name, @DataScope("sn2") String info) {
        System.out.println("info = " + info);
        System.out.println("name = " + name);
        return info;
    }
}
