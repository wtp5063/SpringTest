package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

//    @GetMapping("/upsert_confirm")
//    public String confirm(Model model) {
//        model.addAttribute("title", "確認画面");
//        model.addAttribute("main", "jobUpsertConfirm::main");
//        return "layout";
//    }

    @PostMapping("/upsert_confirm")
    public String submit(@RequestParam String button, @ModelAttribute("job") JobEntity job, Model model, RedirectAttributes redirect)
    {
        if (button.equals("戻る"))
        {
            if (job.getId() == 0)
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
        if (job.getId() == 0)
        {
            boolean result = service.insert(job);
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
            boolean result = service.updateById(job);
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
