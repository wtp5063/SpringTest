package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.EmployersOfferListDto;
import com.example.demo.model.SeekersOfferListDto;

/**
 * offer(customerテーブルとjobテーブルの中間テーブル)テーブルのDao interface。
 * @author tanakamasato
 * @since 2021/01/29
 */
public interface OfferDao
{
    public boolean insert(int costomer_id, int job_id) throws DataAccessException;
    public List<EmployersOfferListDto> findByCustomerIdEmployer(int customer_id) throws DataAccessException;
    public List<SeekersOfferListDto> findByCustomerIdSeeker(int customer_id) throws DataAccessException;
    public boolean deleteById(int id) throws DataAccessException;
}
