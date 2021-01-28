package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.CustomerDao;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報登録or編集画面用Service class。
 * @author tanakamasato
 * @since 2021/01/27
 */
@Service
@RequiredArgsConstructor
public class CustomerUpsertService
{
    /**
     * customer(ユーザー情報)テーブルのdao。
     */
    private final CustomerDao dao;

    /**
     * 受け取ったidと一致するデータを削除する。
     * @param id customer(ユーザー情報)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    public boolean deleteById(int id)
    {
        return dao.deleteById(id);
    }
}
