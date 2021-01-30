package com.example.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.JobEntity;
import com.example.demo.service.JobDetailService;

import lombok.RequiredArgsConstructor;

/**
 * 求人情報詳細画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/27
 */
@Controller
@RequiredArgsConstructor
public class JobDetailController
{

    /**
     * 専用のService class。
     */
    private final JobDetailService service;

    /**
     * 表示処理を行う。
     * @param id job(求人情報)テーブルのプライマリキー。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/job_detail")
    public String detail(@RequestParam("id") int id, Model model) {
        JobEntity job = service.findById(id);
        model.addAttribute("job", job);
        model.addAttribute("title", "SpringTest:求人詳細");
        model.addAttribute("main", "jobDetail::main");
        return "layout";
    }

    /**
     * 受け取った情報をoffer(jobテーブルとcustomerテーブルの中間テーブル)に挿入してホーム画面にリダイレクトする。
     * @param requestParam job(求人情報)テーブルとcustomer(ユーザー情報)テーブルのプライマリキー。
     * @param redirect リダイレクト後に使用する情報を格納する。
     * @return リダイレクト情報。
     */
    @PostMapping("/job_detail")
    public String offer(@RequestParam Map<String, String> requestParam, RedirectAttributes redirect) {
        int customer_id = Integer.parseInt(requestParam.get("customer_id"));
        int job_id = Integer.parseInt(requestParam.get("job_id"));
        boolean result = service.insert(customer_id, job_id);
        if (result)
        {
            redirect.addFlashAttribute("msg", "応募が完了しました");
        }
        else
        {
            redirect.addFlashAttribute("msg", "応募に失敗しました");
        }
        return "redirect:/";
    }
}
