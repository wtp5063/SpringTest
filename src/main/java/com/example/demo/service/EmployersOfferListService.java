package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.EmployersOfferListDto;
import com.example.demo.repository.OfferDao;

import lombok.RequiredArgsConstructor;

/**
 * 採用担当者向け応募リスト用Service class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Service
@RequiredArgsConstructor
public class EmployersOfferListService
{
    /**
     * offer(customerテーブルとjobテーブルの中間テーブル)のDao。
     */
    private final OfferDao offerDao;

    /**
     * 受け取ったcustomer_idと一致するデータを、中間テーブルofferを利用してcustomerテーブルとjobテーブルから取得する。
     * @param customer_id customer(顧客情報)テーブルのプライマリキー。
     * @return 成功時：OfferInformationDtoのインスタンスのList、失敗時：null。
     */
    public List<EmployersOfferListDto> findByCustomerId(int customer_id){
        return offerDao.findByCustomerIdEmployer(customer_id);
    }
}
