package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    /**
     * 専用のService class。
     */
    private final JobUpsertService service;

    /**
     * セッションの取得を行う。
     */
    private final HttpSession session;

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
        if (session.getAttribute("entity") == null)
        {
            session.setAttribute("entity", entity);
        }
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
        session.setAttribute("entity", entity);
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
        return "redirect:/job/upsert_confirm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id, Model model, RedirectAttributes redirect)
    {
        boolean result = service.deleteById(id);
        if (result)
        {
            redirect.addFlashAttribute("msg", "削除に成功しました");
        }
        else
        {
            redirect.addFlashAttribute("msg", "削除に失敗しました");
        }
        return "redirect:/employer";
    }
}
