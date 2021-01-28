package com.example.demo.common.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler
{

    public SuccessHandler()
    {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        Set<String> auth = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String role = auth.stream().findFirst().get();
        System.out.println(role);
        if (role.equals("ROLE_SEEKER"))
        {
            response.sendRedirect("/");
        }
        else
        {
            response.sendRedirect("/employer");
        }
    }

}
