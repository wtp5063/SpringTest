package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

/**
 * 部分一致検索用Service class。
 * @author tanakamasato
 * @since 2021/01/31
 */
@Service
@RequiredArgsConstructor
public class SearchService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao jobDao;

    /**
     * 受け取ったidと一致するレコードを削除する。
     * @param id offer(customerテーブルとjobテーブルの中間テーブル)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    public List<JobEntity> search(String value){
        return jobDao.search(value);
    }
}
