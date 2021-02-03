package com.example.demo.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;
import com.example.demo.service.CustomerUpsertConfirmService;

import lombok.RequiredArgsConstructor;

/**
 * 登録or編集画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerUpsertConfirmController
{
    /**
     * 登録or編集確認画面用Service class。
     */
    private final CustomerUpsertConfirmService service;

//    /**
//     * 表示処理を行う。
//     * @param model
//     * @return thymeleafテンプレート。
//     */
//    @GetMapping("/upsert_confirm")
//    public String confirm(@ModelAttribute("customer") CustomerEntity customer, Model model)
//    {
//        System.out.println(customer.getId());
//        model.addAttribute("title", "確認画面");
//        model.addAttribute("main", "customerUpsertConfirm::main");
//        return "layout";
//    }

    /**
     * 戻るボタンが押されていた場合は登録or編集画面にリダイレクトする。
     * それ以外の場合でセッションから取り出したインスタンスに格納されたidが
     * 0の場合はテーブルにデータを挿入、0以外の場合はidと一致するデータを更新する。
     * @param button 押されたボタンのvalue。
     * @param model
     * @param redirect リダイレクト後使用するデータの格納先。
     * @return リダイレクト処理。
     */
    @PostMapping("/upsert_confirm")
    public String submit(@RequestParam String button,@ModelAttribute("customer") CustomerEntity customer, Model model, RedirectAttributes redirect)
    {
        if (button.equals("戻る"))
        {
            if (customer.getId() == 0)
            {
                model.addAttribute("title", "SpringTest:新規会員登録");
            }
            else
            {
                model.addAttribute("title", "SpringTest:会員情報編集");
            }
            model.addAttribute("main", "customerUpsert::main");
            return "layout";
        }

        //insert(idが0)の場合の処理。
        if (customer.getId() == 0)
        {
            boolean result = service.insert(customer);
            if (result)
            {
                redirect.addFlashAttribute("msg", "登録に成功しました");
            }
            else
            {
                redirect.addFlashAttribute("msg", "登録に失敗しました");
            }
            return "redirect:/login";
        }

        //updateの場合(idが0以外)の場合の処理。
        boolean result = service.updateById(customer);
        if (result)
        {
            redirect.addFlashAttribute("msg", "編集に成功しました");
        }
        else
        {
            redirect.addFlashAttribute("msg", "編集に失敗しました");
        }
        SecurityContext context = SecurityContextHolder.getContext();
        LoginUser user = new LoginUser(customer);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
        return "redirect:/customer_information";
    }
}
