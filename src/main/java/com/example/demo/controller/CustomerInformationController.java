package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;

/**
 * プロフィール表示用Controller class。
 * @author tanakamasato
 * @since 2021/01/27
 */
@Controller
public class CustomerInformationController
{
    /**
     * 表示処理を行う。
     * @param entity customer(ユーザー情報)テーブルのentity class。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/customer_information")
    public String information(@ModelAttribute CustomerEntity entity, Model model) {
        LoginUser user = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getEntity());
        model.addAttribute("title", "プロフィール");
        model.addAttribute("main", "customerInformation::main");
        return "layout";
    }
}
