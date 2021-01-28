package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログイン画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Controller
public class LogInController
{
    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "SpringTest:ログイン画面");
        model.addAttribute("main", "login::main");
        return "layout";
    }
}
