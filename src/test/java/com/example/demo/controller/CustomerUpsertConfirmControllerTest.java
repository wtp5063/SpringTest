package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CustomerEntity;
import com.example.demo.service.CustomerUpsertConfirmService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerUpsertConfirmControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerUpsertConfirmService service;

    @MockBean
    private Model model;

    @MockBean
    private RedirectAttributes redirect;

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @Test
    @WithMockUser
    void 戻るを押してidが0の時() throws Exception
    {
        CustomerEntity customer = new CustomerEntity();
        new CustomerUpsertConfirmController(service).submit("戻る", customer, model, redirect);
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attribute("title", "SpringTest:新規会員登録"))
        .andExpect(model().attribute("main", "customerUpsert::main"))
        .andExpect(view().name("layout"));
    }

    @Test
    void 戻るを押してidが1の時()
    {
        fail("まだ実装されていません");
    }

    @Test
    void testSubmit3()
    {
        fail("まだ実装されていません");
    }

    @Test
    void testSubmit4()
    {
        fail("まだ実装されていません");
    }

    @Test
    void testSubmit5()
    {
        fail("まだ実装されていません");
    }

    @Test
    void testSubmit6()
    {
        fail("まだ実装されていません");
    }
}
