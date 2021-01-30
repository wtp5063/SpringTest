package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.JobEntity;

import lombok.RequiredArgsConstructor;

/**
 * job(求人情報)テーブルのDao実装クラス。
 *
 * @author tanakamasato
 * @since 2021/01/28
 */
@Repository
@RequiredArgsConstructor
public class JobDaoImpl implements JobDao
{
    /**
     * データベースアクセス処理を行う。
     */
    private final JdbcTemplate jdbc;

    /**
     * 受け取ったEntityに格納されたデータをテーブルに挿入する。
     *
     * @param entity job(求人情報)テーブルのEntity class。
     * @return 成功時：true、失敗時：false。
     */
    @Override
    public boolean insert(JobEntity entity) throws DataAccessException
    {
        int rowNum = jdbc.update("INSERT INTO job (customer_id, title, company, description, min_salary, max_salary) VALUES(?, ?, ?, ?, ?, ?)", entity.getCustomer_id(), entity.getTitle(), entity.getCompany(), entity.getDescription(),
                entity.getMin_salary(), entity.getMax_salary());
        return rowNum > 0;
    }

    /**
     * job(求人情報)テーブルのデータを全件取得して返す。
     *
     * @return 成功時：jobテーブルのEntityのList、失敗時：null。
     */
    @Override
    public List<JobEntity> findAll() throws DataAccessException
    {
        try
        {
            List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM job ORDER BY datetime DESC");
            List<JobEntity> jobList = new ArrayList<>();
            for (Map<String, Object> map : getList)
            {
                JobEntity entity = new JobEntity();
                entity.setId((int) map.get("id"));
                entity.setCustomer_id((int) map.get("customer_id"));
                entity.setTitle((String) map.get("title"));
                entity.setCompany((String) map.get("company"));
                entity.setDatetime((Timestamp) map.get("datetime"));
                entity.setDescription((String) map.get("description"));
                entity.setMin_salary(String.valueOf(map.get("min_salary")));
                entity.setMax_salary(String.valueOf(map.get("max_salary")));
                jobList.add(entity);
            }
            return jobList;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }

    }

    /**
     * 受け取ったcustomer_idと一致するデータを取得し、Entityに格納後Listにして返す。
     *
     * @param customer_id cusotmer(ユーザー情報)テーブルのプライマリキー。
     * @return 成功時：jobテーブルのEntity classのList、失敗時：null。
     */
    @Override
    public List<JobEntity> findByCustomerId(int customer_id) throws DataAccessException
    {
        try
        {
            List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM job WHERE customer_id = ? ORDER BY datetime DESC", customer_id);
            List<JobEntity> jobList = new ArrayList<>();
            for (Map<String, Object> map : getList)
            {
                JobEntity entity = new JobEntity();
                entity.setId((int) map.get("id"));
                entity.setCustomer_id((int) map.get("customer_id"));
                entity.setTitle((String) map.get("title"));
                entity.setCompany((String) map.get("company"));
                entity.setDatetime((Timestamp) map.get("datetime"));
                entity.setDescription((String) map.get("description"));
                entity.setMin_salary(String.valueOf(map.get("min_salary")));
                entity.setMax_salary(String.valueOf(map.get("max_salary")));
                jobList.add(entity);
            }
            return jobList;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     * 受け取ったidと一致するデータを取得し、Entityに格納して返す。
     *
     * @param id job(求人情報)テーブルのプライマリキー。
     * @return 成功時：jobテーブルのEntity class、失敗時：null。
     */
    @Override
    public JobEntity findById(int id) throws DataAccessException
    {
        try
        {
            Map<String, Object> map = jdbc.queryForMap("SELECT * FROM job WHERE id = ?", id);
            JobEntity entity = new JobEntity();
            entity.setId((int) map.get("id"));
            entity.setCustomer_id((int) map.get("customer_id"));
            entity.setTitle((String) map.get("title"));
            entity.setCompany((String) map.get("company"));
            entity.setDatetime((Timestamp) map.get("datetime"));
            entity.setDescription((String) map.get("description"));
            entity.setMin_salary(String.valueOf(map.get("min_salary")));
            entity.setMax_salary(String.valueOf(map.get("max_salary")));
            return entity;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     * 受け取ったEntityに格納されたidと一致するデータを、格納されたデータで上書きする。
     *
     * @param job(求人情報)テーブルのEntity class。
     * @return 成功時：true、失敗時：false。
     */
    @Override
    public boolean updateById(JobEntity entity) throws DataAccessException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int rowNum = jdbc.update("UPDATE job SET title = ?, company = ?, datetime = ?, description = ?, min_salary = ?, max_salary = ? WHERE id = ?", entity.getTitle(), entity.getCompany(), timestamp, entity.getDescription(),
                entity.getMin_salary(), entity.getMax_salary(), entity.getId());
        return rowNum > 0;
    }

    /**
     * 受け取ったidと一致するデータを削除する。
     *
     * @param id job(求人情報)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    @Override
    public boolean deleteById(int id) throws DataAccessException
    {
        int rowNum = jdbc.update("DELETE FROM job WHERE id = ?", id);
        return rowNum > 0;
    }
}
