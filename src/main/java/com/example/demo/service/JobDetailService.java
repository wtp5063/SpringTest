package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;
import com.example.demo.repository.OfferDao;

import lombok.RequiredArgsConstructor;

/**
 * 求人詳細用Service class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Service
@RequiredArgsConstructor
public class JobDetailService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao jobDao;

    private final OfferDao offerDao;

    /**
     * 受け取ったidと一致するデータを取得し、Entityに格納して返す。
     * @param id job(求人情報)テーブルのプライマリキー。
     * @return 成功時：jobテーブルのEntity class、失敗時：null。
     */
    public JobEntity findById(int id) {
        return jobDao.findById(id);
    }

    public boolean insert(int customer_id, int job_id) {
        return offerDao.insert(customer_id, job_id);
    }
}
