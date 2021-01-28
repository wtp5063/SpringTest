package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerEntity;
import com.example.demo.repository.CustomerDao;

import lombok.RequiredArgsConstructor;

/**
 * 登録or編集確認画面用Service class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Service
@RequiredArgsConstructor
public class CustomerUpsertConfirmService
{
    private final CustomerDao dao;

    public boolean insert(CustomerEntity entity) {
        return dao.insert(entity);
    }

    public boolean updateById(CustomerEntity entity) {
        return dao.updateById(entity);
    }
}
