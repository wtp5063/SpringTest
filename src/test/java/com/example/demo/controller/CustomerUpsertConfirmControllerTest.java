package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
    void upsert_confirm_戻るを押してidが0の時() throws Exception
    {
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "戻る"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("title", "SpringTest:新規会員登録"))
        .andExpect(model().attribute("main", "customerUpsert::main"))
        .andExpect(view().name("layout"));
    }

    @Test
    void upsert_confirm_戻るを押してidが1の時() throws Exception
    {
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "戻る").param("id", "1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("title", "SpringTest:会員情報編集"))
        .andExpect(model().attribute("main", "customerUpsert::main"))
        .andExpect(view().name("layout"));
    }

    @Test
    void upsert_confirm_OKを押してidが0で登録に成功した時() throws Exception
    {
        when(service.insert(new CustomerEntity())).thenReturn(true);
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "OK"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("msg", "登録に成功しました"))
        .andExpect(redirectedUrl("/login"));
    }

    @Test
    void upsert_confirm_OKを押してidが0で登録に失敗した時() throws Exception
    {
        when(service.insert(new CustomerEntity())).thenReturn(false);
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "OK"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("msg", "登録に失敗しました"))
        .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    void upsert_confirm_OKを押してidが1で更新に成功した時() throws Exception
    {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1);
        entity.setRole("ROLE_EMPLOYER");
        entity.setEmail("test@example.com");
        entity.setPassword("1111aaaa");
        when(service.updateById(entity)).thenReturn(true);
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "OK").param("id", "1").param("role", "ROLE_EMPLOYER").param("email", "test@example.com").param("password", "1111aaaa"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("msg", "編集に成功しました"))
        .andExpect(redirectedUrl("/customer_information"));
    }

    @Test
    @WithMockUser
    void testSubmit6() throws Exception
    {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1);
        entity.setRole("ROLE_EMPLOYER");
        entity.setEmail("test@example.com");
        entity.setPassword("1111aaaa");
        when(service.updateById(entity)).thenReturn(false);
        mockMvc.perform(post("/customer/upsert_confirm").with(csrf()).param("button", "OK").param("id", "1").param("role", "ROLE_EMPLOYER").param("email", "test@example.com").param("password", "1111aaaa"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("msg", "編集に失敗しました"))
        .andExpect(redirectedUrl("/customer_information"));
    }
}
