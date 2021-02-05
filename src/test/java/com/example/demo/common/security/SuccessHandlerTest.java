package com.example.demo.common.security;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

class SuccessHandlerTest
{

    @BeforeAll
    static void setUpBeforeClass() throws Exception
    {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception
    {
    }

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @AfterEach
    void tearDown() throws Exception
    {
    }

    @Test
    void testOnAuthenticationSuccess() throws IOException, ServletException
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authoritie = mock(Authentication.class);
        Set<String> auth = new HashSet<>();
        auth.add("ROLE_SEEKER");
        AuthorityUtils utils = mock(AuthorityUtils.class);
        when(utils.authorityListToSet(authoritie.getAuthorities())).thenReturn(auth);

        new SuccessHandler().onAuthenticationSuccess(request, response, authoritie);
        verify(response).sendRedirect("/");
        verify(response).sendRedirect("/employer");
    }
}
