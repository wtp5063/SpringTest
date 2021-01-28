package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerEntity;
import com.example.demo.repository.CustomerDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DuplicateValidatorService
{
    private final CustomerDao dao;

    public CustomerEntity findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
