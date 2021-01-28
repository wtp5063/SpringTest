package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/employer")
    public String home(Model model) {
        model.addAttribute("title", "SpringTest");
        model.addAttribute("main", "employer::main");
        return "layout";
    }
}
