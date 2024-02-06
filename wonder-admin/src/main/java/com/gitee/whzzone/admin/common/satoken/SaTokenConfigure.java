package com.gitee.whzzone.admin.common.satoken;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.gitee.whzzone.admin.common.filter.GlobalSaServletFilter;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 配置类
 */
@Slf4j
@Configuration
public class SaTokenConfigure {

    @Autowired
    private WonderProperties wonderProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private SaTokenConfig saTokenConfig;

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter saServletFilter() {
        System.out.println("---------- Sa-Token全局过滤器 -----------");
        return new GlobalSaServletFilter()

                // 指定 拦截路由 与 放行路由
                .addInclude("/**").setExcludeList(wonderProperties.getWeb().getIgnorePath())

                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    log.debug("---------- 进入Sa-Token全局认证 -----------");

                    // 登录认证 -- 拦截所有路由
                    SaRouter.match("/**")
                            .notMatch(wonderProperties.getWeb().getIgnorePath())
                            .check(r -> StpUtil.checkLogin())
                    ;

                    log.debug("--------------------------token校验成功");
                    LoginUser loginUserInfo = userService.getLoginUserInfo(Integer.valueOf((String) StpUtil.getLoginId()));
                    SecurityUtil.setLoginUser(loginUserInfo);

                    // 有操作时续签token,避免token突然失效
                    StpUtil.renewTimeout(saTokenConfig.getTimeout());
                })

                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    log.error("---------- 进入Sa-Token异常处理 -----------");
                    // 设置响应头
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    // 使用封装的 JSON 工具类转换数据格式
                    return JSONUtil.toJsonStr(Result.error(Result.UNAUTHORIZED, e.getMessage()));
                })

                // 前置函数：在每次认证函数之前执行（BeforeAuth 不受 includeList 与 excludeList 的限制，所有请求都会进入）
                .setBeforeAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("wonder-server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                    ;
                })

                ;
    }
}
