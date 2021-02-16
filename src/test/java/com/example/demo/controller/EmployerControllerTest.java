package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;
import com.example.demo.model.JobEntity;
import com.example.demo.service.EmployerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployerControllerTest
{
    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private EmployerService service;

    @MockBean
    private SecurityContextHolder contextHolder;

    @MockBean
    private SecurityContext context;

    @MockBean
    private Authentication auth;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockHttpSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    @WithMockUser
    void testEmployerController() throws Exception
    {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class))
        {
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            CustomerEntity expectedCustomer = new CustomerEntity();
            expectedCustomer.setRole("ROLE_SEEKER");
            expectedCustomer.setEmail("mieko@example.com");
            expectedCustomer.setPassword("1111aaaa");
            when(context.getAuthentication()).thenReturn(auth);
            when(auth.getPrincipal()).thenReturn(new LoginUser(expectedCustomer));
            List<JobEntity> jobList = new ArrayList<>();
            when(service.findById(expectedCustomer.getId())).thenReturn(jobList);
            new EmployerController(service).home(model);

            mockMvc.perform(get("/employer").session(mockHttpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("layout"))
            .andExpect(model().attribute("jobList", jobList))
            .andExpect(model().attribute("title", "SpringTest"))
            .andExpect(model().attribute("main", "employer::main"));
        }
    }

}
