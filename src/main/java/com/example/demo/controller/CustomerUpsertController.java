package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.common.validation.InsertGroup;
import com.example.demo.common.validation.order.GroupOrder;
import com.example.demo.model.CustomerEntity;
import com.example.demo.service.CustomerUpsertService;

import lombok.RequiredArgsConstructor;

/**
 * 登録or編集画面用Controller class。
 *
 * @author tanakamasato
 * @since 2021/01/26
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerUpsertController
{
    /**
     * セッションを取得する。
     */
    private final HttpSession session;

    /**
     * バリデーションを行う。
     */
    private final Validator validator;

    /**
     * 専用のService class。
     */
    private final CustomerUpsertService service;

    /**
     * 表示処理を行う。
     * セッションにインスタンスが格納されていなければ格納する。
     * entityに格納されているidが0か0以外かでmodelに格納するタイトルを変える。
     * @param customer customer(ユーザー情報)テーブルのEntity class。
     * @param model
     * @return thymeleafテンプレート。
     */
    @GetMapping("/upsert")
    public String input(@ModelAttribute("customer") CustomerEntity customer, Model model) {
        System.out.println(customer.getId());
        if (session.getAttribute("customer") == null)
        {
            session.setAttribute("customer", customer);
        }
        if (customer.getId() == 0)
        {
            model.addAttribute("title", "SpringTest:新規会員登録");
        } else {
            model.addAttribute("title", "SpringTest:会員情報編集");
        }
        model.addAttribute("main", "customerUpsert::main");
        return "layout";
    }

    /**
     * エラーがあれば登録or編集画面を再度開き、
     * エラーが無ければ確認画面にリダイレクトする。
     * @param customer customer(ユーザー情報)テーブルのEntity class。
     * @param bindingResult エラー情報。
     * @param model
     * @return リダイレクト処理。
     */
    @PostMapping("/upsert")
    public String update(@ModelAttribute("customer") CustomerEntity customer, Errors errors, Model model)
    {

        session.setAttribute("customer", customer);
        List<Object> groupList = new ArrayList<>();
        if(customer.getId() == 0) {
            groupList.add(InsertGroup.class);
        }
        groupList.add(GroupOrder.class);
        ValidationUtils.invokeValidator(validator,(Object) customer, errors, groupList.toArray());
        if (errors.hasErrors())
        {
            if (customer.getId() == 0)
            {
                model.addAttribute("title", "SpringTest:新規会員登録");
            } else {
                model.addAttribute("title", "SpringTest:会員情報編集");
            }
            model.addAttribute("main", "customerUpsert::main");
            return "layout";
        }
        return "redirect:/customer/upsert_confirm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        boolean result = service.deleteById(Integer.parseInt(id));
        if(!result) {
            CustomerEntity entity = (CustomerEntity) session.getAttribute("customer");
            if (entity.getId() == 0)
            {
                model.addAttribute("title", "SpringTest:新規会員登録");
            } else {
                model.addAttribute("title", "SpringTest:会員情報編集");
            }
            model.addAttribute("main", "customerUpsert::main");
            return "layout";
        }
        return "redirect:/logout";
    }
}
