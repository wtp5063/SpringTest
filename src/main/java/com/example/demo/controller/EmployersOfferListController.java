package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.EmployersOfferListDto;
import com.example.demo.service.EmployersOfferListService;

import lombok.RequiredArgsConstructor;

/**
 * 採用担当者向け応募一覧画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/30
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/employer")
public class EmployersOfferListController
{
    /**
     * 専用のService class。
     */
    private final EmployersOfferListService service;

    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/offer_list")
    public String list(Model model) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int customer_id = loginUser.getEntity().getId();
        List<EmployersOfferListDto> offerList = service.findByCustomerId(customer_id);
        model.addAttribute("offerList", offerList);
        model.addAttribute("title", "応募一覧");
        model.addAttribute("main", "employersOfferList::main");
        return "layout";
    }
}
