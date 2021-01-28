package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.common.validation.order.GroupOrder;
import com.example.demo.model.JobEntity;
import com.example.demo.service.JobUpsertService;

import lombok.RequiredArgsConstructor;

/**
 * 求人情報登録or編集画面用Controller class。
 *
 * @author tanakamasato
 * @since 2021/01/28
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobUpsertController
{
    private final JobUpsertService upsertService;

    /**
     * 表示処理を行う。
     *
     * @param entity job(求人情報)テーブルのentity class。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/upsert")
    public String input(@ModelAttribute("entity") JobEntity entity, Model model)
    {
        if (entity.getId() == 0)
        {
            model.addAttribute("title", "新規会員登録");
        }
        else
        {
            model.addAttribute("title", "会員情報編集");
        }
        model.addAttribute("main", "jobUpsert::main");
        return "layout";
    }

    @PostMapping("/upsert")
    public String submit(@ModelAttribute("entity") @Validated(GroupOrder.class) JobEntity entity, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            if (entity.getId() == 0)
            {
                model.addAttribute("title", "新規会員登録");
            }
            else
            {
                model.addAttribute("title", "会員情報編集");
            }
            model.addAttribute("main", "jobUpsert::main");
            return "layout";
        }
        boolean result = upsertService.insert(entity);
        if (result)
        {
            model.addAttribute("msg", "登録に成功しました");
        }
        else
        {
            model.addAttribute("msg", "登録に失敗しました");
        }
        return "redirect:/employer";
    }
}
