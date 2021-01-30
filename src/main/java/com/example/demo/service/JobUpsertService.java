package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

/**
 * 求人情報登録or編集画面用Service class。
 * @author tanakamasato
 * @since 2021/01/28
 */
@Service
@RequiredArgsConstructor
public class JobUpsertService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao jobDao;

    /**
     * 受け取ったidと一致するデータを削除する。
     * @param id job(求人情報)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    public boolean deleteById(int id) {
        return jobDao.deleteById(id);
    }
}
