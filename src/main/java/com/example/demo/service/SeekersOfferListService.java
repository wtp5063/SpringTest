package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.SeekersOfferListDto;
import com.example.demo.repository.OfferDao;

import lombok.RequiredArgsConstructor;

/**
 * 求職者向け応募一覧画面用Service class。
 * @author tanakamasato
 * @since 2021/01/30
 */
@Service
@RequiredArgsConstructor
public class SeekersOfferListService
{
    /**
     * offer(customerテーブルとjobテーブルの中間テーブル)テーブルのDao。
     */
    private final OfferDao dao;

    /**
     * 受け取ったcutomer_idと一致するデータを探し、Dtoに格納する。
     * @param customer_id cusotmer(ユーザー情報)テーブルのプライマリキー。
     * @return 成功時：求職者向け応募一覧画面用Dto class、失敗時：null。
     */
    public List<SeekersOfferListDto> findByCutomerId(int customer_id){
        return dao.findByCustomerIdSeeker(customer_id);
    }

    /**
     * 受け取ったidと一致するレコードを削除する。
     * @param id offer(customerテーブルとjobテーブルの中間テーブル)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    public boolean deleteById(int id) {
        return dao.deleteById(id);
    }
}
