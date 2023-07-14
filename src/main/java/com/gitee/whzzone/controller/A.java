package com.gitee.whzzone.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
public class A {

    public List<Long> getIds(){
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        return ids;
    }

    public List<Long> getByName(String name){
        System.out.println("String name = " + name);

        List<Long> ids = new ArrayList<>();
        ids.add(4L);
        return ids;
    }

    public List<Long> getByName(String name, Integer age){
        System.out.println("name = " + name);
        System.out.println("age = " + age);

        List<Long> ids = new ArrayList<>();
        ids.add(5L);
        return ids;
    }

    public List<Long> getByName(int age){
        System.out.println("int age = " + age);
        List<Long> ids = new ArrayList<>();
        ids.add((long) age);
        return ids;
    }

}
