package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerInformationControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;

    @MockBean
    private SecurityContextHolder securityContext;

    @MockBean
    private SecurityContext context;

    @MockBean
    private Authentication authentication;

    @MockBean
    private Model model;

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockHttpSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    @WithMockUser
    void testInformation() throws Exception
    {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class))
        {
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            CustomerEntity expectedCustomer = new CustomerEntity();
            expectedCustomer.setRole("ROLE_SEEKER");
            expectedCustomer.setEmail("mieko@example.com");
            expectedCustomer.setPassword("1111aaaa");
            when(context.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(new LoginUser(expectedCustomer));
            new CustomerInformationController().information(new CustomerEntity(), model);

            HttpServletRequest request;
            request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

            mockMvc.perform(get("/customer_information").session(mockHttpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("layout"))
            .andExpect(model().attribute("customer", expectedCustomer))
            .andExpect(model().attribute("title", "プロフィール"))
            .andExpect(model().attribute("main", "customerInformation::main"));
        }
    }
}
