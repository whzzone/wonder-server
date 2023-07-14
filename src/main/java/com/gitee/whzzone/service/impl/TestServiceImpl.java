package com.gitee.whzzone.service.impl;

import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class TestServiceImpl implements TestService {

    @Override
    public List<Long> injectTest(String name, @DataScope("sn2") List<Long> scopeList){
//    public List<Long> injectTest(String name, List<Long> scopeList){
        System.out.println("scopeList = " + scopeList);
        System.out.println("injectTest name = " + name);
        return scopeList;
    }
}
