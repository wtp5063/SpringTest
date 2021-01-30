package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.JobEntity;

/**
 * Job(求人情報)テーブルのDao class。
 * @author tanakamasato
 * @since 2021/01/26
 */
public interface JobDao
{
    public boolean insert(JobEntity entity) throws DataAccessException;
    public List<JobEntity> findAll() throws DataAccessException;
    public List<JobEntity> findByCustomerId(int customer_id) throws DataAccessException;
    public JobEntity findById(int id) throws DataAccessException;
    public boolean updateById(JobEntity entity) throws DataAccessException;
    public boolean deleteById(int id) throws DataAccessException;
}
