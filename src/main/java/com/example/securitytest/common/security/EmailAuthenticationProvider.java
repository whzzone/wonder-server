package com.example.securitytest.common.security;

import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.service.EmailService;
import com.example.securitytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author : whz
 * @date : 2023/5/18 8:40
 */
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService sysUserService;

    @Autowired
    private EmailService emailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String code = (String) authentication.getCredentials();

        EmailAuthenticationToken authenticationToken = new EmailAuthenticationToken(email, code);

        User sysUser = sysUserService.getByEmail(email);

        sysUserService.verifyUser(sysUser);

        if (!emailService.verifyEmailCode(email, code)) {
            throw new BadCredentialsException("验证码错误");
        }

        authenticationToken.setDetails(sysUser);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
