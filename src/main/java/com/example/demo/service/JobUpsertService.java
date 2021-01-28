package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobUpsertService
{
    private final JobDao jobDao;

    public boolean insert(JobEntity entity) {
        return jobDao.insert(entity);
    }
}
