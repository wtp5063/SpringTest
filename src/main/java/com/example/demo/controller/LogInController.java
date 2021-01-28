package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("msg", "ログインできませんでした");
        }
        model.addAttribute("title", "SpringTest:ログイン画面");
        model.addAttribute("main", "login::main");
        return "layout";
    }
}
