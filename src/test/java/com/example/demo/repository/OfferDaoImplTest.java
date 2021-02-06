package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.EmployersOfferListDto;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.SeekersOfferListDto;

@SpringBootTest
class OfferDaoImplTest
{
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private OfferDao dao;

    @BeforeAll
    static void setUpBeforeClass() throws Exception
    {
        System.out.println("setUpBeforeClass()");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception
    {
        System.out.println("tearDownAfterClass()");
    }

    @BeforeEach
    void setUp() throws Exception
    {
        System.out.println("setUp()");
        jdbc.update("INSERT INTO customer VALUES(10000, 'テスト2', 'test2@example.com', '22222222', 'テスト市2', 'ROLE_TEST2', '2222222222')");
        jdbc.update("INSERT INTO customer VALUES(9999, 'テスト', 'test@example.com', '11111111', 'テスト市', 'ROLE_TEST', '1111111111')");
        jdbc.update("INSERT INTO job VALUES(999, 9999, 'test', 'テスト株式会社', '2021-02-02 11:18:48.0', 'テストです', '10000', '20000')");
        jdbc.update("INSERT INTO offer(id, customer_id, job_id, datetime) VALUES(99999, 10000, 999, '2021-02-02 11:11:11')");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        System.out.println("tearDown()");
        jdbc.update("DELETE FROM customer WHERE id = 10000");
        jdbc.update("DELETE FROM customer WHERE id = 9999");
    }

    @Test
    void testInsert()
    {
        jdbc.update("INSERT INTO customer (id) VALUES (10001)");
        dao.insert(10001, 999);
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM offer WHERE customer_id = 10001");
        OfferEntity actual = new OfferEntity();
        actual.setCustomer_id((int) map.get("customer_id"));
        actual.setJob_id((int) map.get("job_id"));
        OfferEntity expected = new OfferEntity();
        expected.setCustomer_id(10001);
        expected.setJob_id(999);
        assertEquals(expected, actual);
        jdbc.update("DELETE FROM customer WHERE id = 10001");
    }

    @Test
    void testFindByCustomerIdEmployer() throws ParseException
    {
        List<EmployersOfferListDto> actual = dao.findByCustomerIdEmployer(9999);
        List<EmployersOfferListDto> expected = new ArrayList<>();
        EmployersOfferListDto dto = new EmployersOfferListDto();
        dto.setId(99999);
        dto.setTitle("test");
        dto.setName("テスト2");
        dto.setEmail("test2@example.com");
        dto.setAddress("テスト市2");
        dto.setTel("2222222222");
        Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:11:11").getTime());
        dto.setDatetime(ts);
        expected.add(dto);
        assertEquals(expected, actual);
        actual = dao.findByCustomerIdEmployer(0);
        expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void testFindByCustomerIdSeeker() throws ParseException
    {
        List<SeekersOfferListDto> actual = dao.findByCustomerIdSeeker(10000);
        List<SeekersOfferListDto> expected = new ArrayList<>();
        SeekersOfferListDto dto = new SeekersOfferListDto();
        dto.setId(99999);
        dto.setTitle("test");
        dto.setCompany("テスト株式会社");
        dto.setMin_salary(10000);
        dto.setMax_salary(20000);
        Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:11:11").getTime());
        dto.setDatetime(ts);
        expected.add(dto);
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteById()
    {
        jdbc.update("INSERT INTO offer(id, customer_id, job_id) VALUES(100000, 10000, 999)");
        dao.deleteById(100000);
        String actual;
        try
        {
            Map<String, Object> map = jdbc.queryForMap("SELECT * FROM offer WHERE id = 100000");
            actual = (String) map.get("id");
        }
        catch (EmptyResultDataAccessException e)
        {
            actual = null;
        }
        assertEquals(null, actual);
    }

}
