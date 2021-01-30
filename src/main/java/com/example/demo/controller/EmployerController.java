package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.JobEntity;
import com.example.demo.service.EmployerService;

import lombok.RequiredArgsConstructor;

/**
 * 採用担当向けホーム画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/27
 */
@Controller
@RequiredArgsConstructor
public class EmployerController
{
    private final EmployerService service;

    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/employer")
    public String home(Model model) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<JobEntity> jobList = service.findById(loginUser.getEntity().getId());
        model.addAttribute("jobList", jobList);
        model.addAttribute("title", "SpringTest");
        model.addAttribute("main", "employer::main");
        return "layout";
    }
}
