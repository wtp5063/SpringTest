package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.JobEntity;
import com.example.demo.service.SearchService;

import lombok.RequiredArgsConstructor;

/**
 * 部分一致検索用Controller class。
 * @author tanakamasato
 * @since 2021/01/31
 */
@Controller
@RequiredArgsConstructor
public class SearchController
{
    /**
     * 専用のService class。
     */
    private final SearchService service;

    @GetMapping("/search")
    public String search(@RequestParam String value, Model model) {
        List<JobEntity> jobList = service.search(value);
        model.addAttribute("jobList", jobList);
        model.addAttribute("title", "検索結果");
        model.addAttribute("main", "search::main");
        return "layout";
    }
}
