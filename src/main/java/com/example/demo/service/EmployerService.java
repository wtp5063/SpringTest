package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

/**
 * 採用担当者向けホーム画面用Service class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Service
@RequiredArgsConstructor
public class EmployerService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao jobDao;

    /**
     * 受け取ったcustomer_idと一致するデータを取得し、Entityに格納後Listにして返す。
     * @param customer_id customer(ユーザー情報)テーブルのプライマリキー。
     * @return 成功時：job(求人情報)テーブルのEntityのList、失敗時：null。
     */
    public List<JobEntity> findById(int customer_id){
        return jobDao.findByCustomerId(customer_id);
    }
}
