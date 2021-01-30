package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobEntity;
import com.example.demo.repository.JobDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobUpsertConfirmService
{
    /**
     * job(求人情報)テーブルのDao。
     */
    private final JobDao jobDao;

    /**
     * 受け取ったEntityに格納されたデータをテーブルに挿入する。
     * @param entity job(求人情報)テーブルのEntity class。
     * @return 成功時：true、失敗時：false。
     */
    public boolean insert(JobEntity entity) {
        return jobDao.insert(entity);
    }

    /**
     * 受け取ったEntityに格納されたidと一致するデータを、格納されたデータで上書きする。
     * @param job(求人情報)テーブルのEntity class。
     * @return 成功時：true、失敗時：false。
     */
    public boolean updateById(JobEntity entity) {
        return jobDao.updateById(entity);
    }

}
