package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

/**
 * ホーム画面用Service class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Service
@RequiredArgsConstructor
public class IndexService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao dao;

    /**
     * job(求人情報)テーブルのデータを全件取得して返す。
     * @return 成功時：jobテーブルのEntityのList、失敗時：null。
     */
    public List<JobEntity> findAll(){
        return dao.findAll();
    }
}
