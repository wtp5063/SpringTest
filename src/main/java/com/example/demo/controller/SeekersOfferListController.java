package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.SeekersOfferListDto;
import com.example.demo.service.SeekersOfferListService;

import lombok.RequiredArgsConstructor;

/**
 * 求職者向け応募一覧画面用Controller class。
 * @author tanakamasato
 * @since 2021/01/30
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/seeker")
public class SeekersOfferListController
{
    /**
     * 専用のService class。
     */
    private final SeekersOfferListService service;

    @GetMapping("/offer_list")
    public String list(Model model) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int customer_id = loginUser.getEntity().getId();
        List<SeekersOfferListDto> offerList = service.findByCutomerId(customer_id);
        model.addAttribute("offerList", offerList);
        model.addAttribute("title", "応募一覧");
        model.addAttribute("main", "seekersOfferList::main");
        return "layout";
    }

    @PostMapping("delete")
    public String delete(@RequestParam int id, RedirectAttributes redirect) {
        boolean result = service.deleteById(id);
        if (result)
        {
            redirect.addFlashAttribute("msg", "応募を取り消しました");
        }
        else
        {
            redirect.addFlashAttribute("msg", "取り消しに失敗しました");
        }
        return "redirect:/seeker/offer_list";
    }
}