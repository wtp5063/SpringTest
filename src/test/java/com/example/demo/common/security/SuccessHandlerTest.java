package com.example.demo.common.security;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    @ParameterizedTest
    @CsvSource({"'ROLE_SEEKER', '/spring_test/'", "'ROLE_EMPLOYER', '/spring_test/employer'"})
    void testOnAuthenticationSuccess(String role, String expected) throws IOException, ServletException
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication auth = mock(Authentication.class);

        Collection<GrantedAuthority> list = AuthorityUtils.createAuthorityList(role);

        // https://stackoverflow.com/questions/10554955/mock-method-with-generic-and-extends-in-return-type
        doReturn(list).when(auth).getAuthorities();
        System.out.println(auth.getAuthorities());
        new SuccessHandler().onAuthenticationSuccess(request, response, auth);
        verify(response).sendRedirect(expected);
    }
}
