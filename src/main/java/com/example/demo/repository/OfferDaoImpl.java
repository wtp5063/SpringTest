package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmployersOfferListDto;
import com.example.demo.model.SeekersOfferListDto;

import lombok.RequiredArgsConstructor;

/**
 * offer(customerテーブルとjobテーブルの中間テーブル)のDaoの実装クラス。
 *
 * @author tanakamasato
 * @since 2021/01/29
 */
@Repository
@RequiredArgsConstructor
public class OfferDaoImpl implements OfferDao
{
    /**
     * データベースアクセス処理を行う。
     */
    private final JdbcTemplate jdbc;

    /**
     * 受け取ったパラメーターをテーブルに挿入する。
     *
     * @param customer_id customer(ユーザー情報)テーブルのプライマリキー。
     * @param job_id      job(求人情報)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    @Override
    public boolean insert(int customer_id, int job_id) throws DataAccessException
    {
        int rowNum = jdbc.update("INSERT INTO offer (customer_id, job_id) VALUES (?, ?)", customer_id, job_id);
        return rowNum > 0;
    }

    /**
     * 受け取ったcustomer_idと一致するデータを、中間テーブルofferを利用してcustomerテーブルとjobテーブルから取得する。
     *
     * @param customer_id customer(顧客情報)テーブルのプライマリキー。
     * @return 成功時：OfferInformationDtoのインスタンスのList、失敗時：null。
     */
    @Override
    public List<EmployersOfferListDto> findByCustomerIdEmployer(int customer_id) throws DataAccessException
    {
        try
        {
            List<Map<String, Object>> getList = jdbc.queryForList("SELECT offer.id, offer.datetime, job.title, customer.name, customer.email, customer.address, customer.tel"
                    + " FROM offer JOIN job ON offer.job_id = job.id JOIN customer ON offer.customer_id = customer.id WHERE job.customer_id = ? ORDER BY offer.datetime DESC", customer_id);
            List<EmployersOfferListDto> dtoList = new ArrayList<>();
            for (Map<String, Object> map : getList)
            {
                EmployersOfferListDto dto = new EmployersOfferListDto();
                dto.setId((int) map.get("id"));
                dto.setDatetime((Timestamp) map.get("datetime"));
                dto.setTitle((String) map.get("title"));
                dto.setName((String) map.get("name"));
                dto.setEmail((String) map.get("email"));
                dto.setAddress((String) map.get("address"));
                dto.setTel((String) map.get("tel"));
                dtoList.add(dto);
            }
            return dtoList;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     * 受け取ったcutomer_idと一致するデータを探し、Dtoに格納する。
     * @param customer_id cusotmer(ユーザー情報)テーブルのプライマリキー。
     * @return 成功時：求職者向け応募一覧画面用Dto class、失敗時：null。
     */
    @Override
    public List<SeekersOfferListDto> findByCustomerIdSeeker(int customer_id) throws DataAccessException
    {
        try
        {
            List<Map<String, Object>> getList = jdbc.queryForList("SELECT offer.id, offer.datetime, job.title, job.company, job.min_salary, job.max_salary FROM offer JOIN job ON offer.job_id = job.id WHERE offer.customer_id = ?",
                    customer_id);
            List<SeekersOfferListDto> dtoList = new ArrayList<>();
            for (Map<String, Object> map : getList)
            {
                SeekersOfferListDto dto = new SeekersOfferListDto();
                dto.setId((int) map.get("id"));
                dto.setDatetime((Timestamp) map.get("datetime"));
                dto.setTitle((String) map.get("title"));
                dto.setCompany((String) map.get("company"));
                dto.setMin_salary((int) map.get("min_salary"));
                dto.setMax_salary((int) map.get("max_salary"));
                dtoList.add(dto);
            }
            return dtoList;
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     * 受け取ったidと一致するレコードを削除する。
     * @param id offer(customerテーブルとjobテーブルの中間テーブル)テーブルのプライマリキー。
     * @return 成功時：true、失敗時：false。
     */
    @Override
    public boolean deleteById(int id) throws DataAccessException
    {
        int rowNum = jdbc.update("DELETE FROM offer WHERE id = ?", id);
        return rowNum > 0;
    }

}
