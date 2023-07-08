package com.example.securitytest.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : whz
 * @date : 2023/2/7 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteVo {
    private String title;
    private String path;
    private String component;
    private Boolean auth;

}
