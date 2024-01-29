package com.gitee.whzzone.admin.common.security;

import com.gitee.whzzone.admin.common.filter.TokenFilter;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
// 开启注解设置权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WonderProperties wonderProperties;

    @Lazy
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public AuthSuccessHandler authSuccessHandler;

    @Autowired
    public AuthFailureHandler authFailureHandler;

    @Autowired
    private TokenFilter tokenFilter;

//    @Autowired
//    private EmailAuthenticationProvider emailAuthenticationProvider;

    // 配置密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 配置认证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
//                .and()
//                .authenticationProvider(emailAuthenticationProvider)
        ;
    }
    
    // 配置安全策略
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .successHandler(authSuccessHandler)  //认证成功处理器
                .failureHandler(authFailureHandler)  //认证失败处理器
                .disable()
                .authorizeRequests()
                .antMatchers(wonderProperties.getWeb().getIgnorePath().toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 不创建session
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(anonymousAuthenticationHandler)  //匿名无权限访问
//                .accessDeniedHandler(customerAccessDeniedHandler)          //认证用户无权限访问
                .and().cors();//开启跨域配置

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
