package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.JobEntity;
import com.example.demo.service.IndexService;

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
    private final IndexService service;

    /**
     * 表示処理を行う。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/")
    public String index(Model model) {
        List<JobEntity> jobList = service.findAll();
        model.addAttribute("jobList", jobList);
        model.addAttribute("title", "SpringTest");
        model.addAttribute("main", "index::main");
        return "layout";
    }
}
