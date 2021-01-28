package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * ホーム画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Controller
@RequiredArgsConstructor
public class IndexController
{

    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "SpringTest");
        model.addAttribute("main", "index::main");
        return "layout";
    }
}
