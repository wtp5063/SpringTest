package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.JobEntity;

@SpringBootTest
class JobDaoImplTest
{
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private JobDao dao;

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
        jdbc.update("INSERT INTO customer VALUES(9999, 'テスト', 'test@example.com', '11111111', 'テスト市', 'ROLE_TEST', '1111111111')");
        jdbc.update("INSERT INTO job VALUES(999, 9999, 'test', 'テスト株式会社', '2021-02-02 11:18:48.0', 'テストです', '10000', '20000')");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        System.out.println("tearDown()");
        jdbc.update("DELETE FROM job WHERE id = 999");
        jdbc.update("DELETE FROM customer WHERE id = 9999");
    }

    @Test
    void testInsert()
    {
        JobEntity expected = new JobEntity();
        expected.setCustomer_id(9999);
        expected.setTitle("タイトル");
        expected.setCompany("会社名");
        expected.setDescription("内容");
        expected.setMin_salary("1000");
        expected.setMax_salary("2000");

        boolean actualRetValue = dao.insert(expected);
        assertTrue(actualRetValue);
        JobEntity entity = new JobEntity();
        actualRetValue = dao.insert(entity);
        assertFalse(actualRetValue);

        JobEntity actual = jdbc.queryForObject("SELECT * FROM job WHERE title = 'タイトル' AND description = '内容'", new BeanPropertyRowMapper<JobEntity>(JobEntity.class));
        Timestamp ts = (Timestamp) actual.getDatetime();
        LocalDateTime ldt = ts.toLocalDateTime();
        LocalDate actualDate = ldt.toLocalDate();
        LocalDate expectedDate = LocalDate.now();
        actual.setId(0);
        actual.setDatetime(null);
        assertEquals(expected, actual);
        assertEquals(expectedDate, actualDate);

        jdbc.update("DELETE FROM job WHERE title = 'タイトル' AND description = '内容'");
    }

    @Test
    void testFindAll()
    {
        List<JobEntity> actual = dao.findAll();
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM job ORDER BY datetime DESC");
        List<JobEntity> expected = new ArrayList<>();
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
            expected.add(entity);
        }
        assertEquals(expected, actual);
    }

    @Test
    void testFindByCustomerId() throws ParseException
    {
        List<JobEntity> actual = dao.findByCustomerId(9999);
        List<JobEntity> expected = new ArrayList<>();
        JobEntity entity = new JobEntity();
        entity.setId(999);
        entity.setCustomer_id(9999);
        entity.setTitle("test");
        entity.setCompany("テスト株式会社");
        Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:18:48.0").getTime());
        entity.setDatetime(ts);
        entity.setDescription("テストです");
        entity.setMin_salary("10000");
        entity.setMax_salary("20000");
        expected.add(entity);
        assertEquals(expected, actual);
        actual = dao.findByCustomerId(0);
        expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void testFindById() throws ParseException
    {
        JobEntity actual = dao.findById(999);
        JobEntity expected = new JobEntity();
        expected.setId(999);
        expected.setCustomer_id(9999);
        expected.setTitle("test");
        expected.setCompany("テスト株式会社");
        Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:18:48.0").getTime());
        expected.setDatetime(ts);
        expected.setDescription("テストです");
        expected.setMin_salary("10000");
        expected.setMax_salary("20000");
        assertEquals(expected, actual);
        actual = dao.findById(0);
        assertEquals(null, actual);
    }

    @Test
    void testUpdateById()
    {
        JobEntity expected = new JobEntity();
        expected.setId(999);
        expected.setCustomer_id(9999);
        expected.setTitle("update");
        expected.setCompany("update.co");
        expected.setDescription("アップデート");
        expected.setMin_salary("3000");
        expected.setMax_salary("5000");

        boolean actualRetValue = dao.updateById(expected);
        assertTrue(actualRetValue);

        JobEntity actual = jdbc.queryForObject("SELECT * FROM job WHERE id = 999", new BeanPropertyRowMapper<JobEntity>(JobEntity.class));
        Timestamp ts = (Timestamp) actual.getDatetime();
        LocalDateTime ldt = ts.toLocalDateTime();
        LocalDate actualDate = ldt.toLocalDate();
        LocalDate expectedDate = LocalDate.now();
        actual.setDatetime(null);
        assertEquals(expected, actual);
        assertEquals(expectedDate, actualDate);
        expected.setId(5000);
        actualRetValue = dao.updateById(expected);
        assertFalse(actualRetValue);
    }

    @Test
    void testDeleteById()
    {
        jdbc.update("INSERT INTO job(id, customer_id, title, company, description, min_salary, max_salary) VALUES(1000, 9999, 'delete', 'delete.co', '削除', '7000', '8000')");
        boolean actualRetValue = dao.deleteById(1000);
        assertTrue(actualRetValue);
        actualRetValue = dao.deleteById(5000);
        assertFalse(actualRetValue);

        JobEntity actual;
        try
        {
            actual = jdbc.queryForObject("SELECT * FROM job WHERE id = 1000",new BeanPropertyRowMapper<JobEntity>(JobEntity.class));
        }
        catch (EmptyResultDataAccessException e)
        {
            actual = null;
        }
        assertEquals(null, actual);
    }

    @Test
    void testSearch() throws ParseException
    {
        List<JobEntity> actual = dao.search("テストで");
        List<JobEntity> expected = new ArrayList<>();
        JobEntity entity = new JobEntity();
        entity.setId(999);
        entity.setCustomer_id(9999);
        entity.setTitle("test");
        entity.setCompany("テスト株式会社");
        Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:18:48.0").getTime());
        entity.setDatetime(ts);
        entity.setDescription("テストです");
        entity.setMin_salary("10000");
        entity.setMax_salary("20000");
        expected.add(entity);
        assertEquals(expected, actual);
        actual = dao.search("falseです");
        expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void test() throws ParseException {
       JobEntity actual = jdbc.queryForObject("SELECT * FROM job WHERE id = 999", new BeanPropertyRowMapper<JobEntity>(JobEntity.class));
       JobEntity expected = new JobEntity();
       expected.setId(999);
       expected.setCustomer_id(9999);
       expected.setTitle("test");
       expected.setCompany("テスト株式会社");
       Timestamp ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-02-02 11:18:48.0").getTime());
       expected.setDatetime(ts);
       expected.setDescription("テストです");
       expected.setMin_salary("10000");
       expected.setMax_salary("20000");
       assertEquals(expected, actual);
    }
}
