package com.example.securitytest.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Integer id;
    private String username;
    private String phone;
    private String name;
    private String email;
    private Date createTime;
    private Date updateTime;
}
