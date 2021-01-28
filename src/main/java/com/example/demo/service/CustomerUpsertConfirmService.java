package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerEntity;
import com.example.demo.repository.CustomerDao;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報登録or編集確認画面用Service class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Service
@RequiredArgsConstructor
public class CustomerUpsertConfirmService
{
    /**
     * ユーザー情報テーブルのdao。
     */
    private final CustomerDao dao;

    /**
     * 受け取った情報をテーブルに挿入する。
     * @param entity customer(ユーザー情報)テーブルのentity class。
     * @return 成功時：true、失敗時：false。
     */
    public boolean insert(CustomerEntity entity) {
        return dao.insert(entity);
    }

    /**
     * 受け取ったインスタンスに格納されたidと一致するテーブルの情報を格納されたデータで上書きする。
     * @param entity customer(ユーザー情報)テーブルのentity class。
     * @return 成功時：true、失敗時：false。
     */
    public boolean updateById(CustomerEntity entity) {
        return dao.updateById(entity);
    }
}
