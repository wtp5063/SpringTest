package com.example.demo.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.CustomerEntity;

/**
 * customer(ユーザー情報)テーブルのDao class。
 * @author tanakamasato
 * @since 2021/01/26
 */
public interface CustomerDao
{
    public boolean insert(CustomerEntity entity) throws DataAccessException;
    public CustomerEntity findById(int id) throws DataAccessException ;
    public CustomerEntity findByEmail(String email) throws DataAccessException;
    public boolean updateById(CustomerEntity entity) throws DataAccessException;
    public boolean deleteById(int id) throws DataAccessException;
}
