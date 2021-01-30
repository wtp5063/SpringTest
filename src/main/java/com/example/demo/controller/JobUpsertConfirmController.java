package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.JobEntity;
import com.example.demo.service.JobUpsertConfirmService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobUpsertConfirmController
{
    /**
     * 専用のService class。
     */
    private final JobUpsertConfirmService service;

    /**
     * セッションの取得を行う。
     */
    private final HttpSession session;

    @GetMapping("/upsert_confirm")
    public String confirm(Model model) {
        model.addAttribute("title", "確認画面");
        model.addAttribute("main", "jobUpsertConfirm::main");
        return "layout";
    }

    @PostMapping("/upsert_confirm")
    public String submit(@RequestParam String button, Model model, RedirectAttributes redirect) {
        if (button.equals("戻る"))
        {
            return "redirect:/job/upsert";
        }
        JobEntity entity = (JobEntity) session.getAttribute("entity");
        session.removeAttribute("entity");
        if (entity.getId() == 0)
        {
            boolean result = service.insert(entity);
            if (result)
            {
                redirect.addFlashAttribute("msg", "登録に成功しました");
            }
            else
            {
                redirect.addFlashAttribute("msg", "登録に失敗しました");
            }
        }
        else
        {
            boolean result = service.updateById(entity);
            if (result)
            {
                redirect.addFlashAttribute("msg", "編集に成功しました");
            }
            else
            {
                redirect.addFlashAttribute("msg", "編集に失敗しました");
            }
        }
        return "redirect:/employer";
    }
}
