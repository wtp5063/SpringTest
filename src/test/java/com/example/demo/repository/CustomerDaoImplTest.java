package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.CustomerEntity;

@SpringBootTest
@EnableWebMvc
class CustomerDaoImplTest
{
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private CustomerDao dao;

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
        jdbc.update("INSERT INTO customer VALUES(999, 'test', 'test@example.com', '11111111', 'テスト市', 'ROLE_TEST', '1122223333')");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        System.out.println("tearDown()");
        jdbc.update("DELETE FROM customer WHERE id = 999");
    }

    @Test
    void testInsert()
    {
        CustomerEntity expected = new CustomerEntity();
        expected.setName("名前");
        expected.setEmail("email@example.com");
        expected.setPassword("password1");
        expected.setAddress("住所");
        expected.setTel("0000000000");
        expected.setRole("権限");
        dao.insert(expected);
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM customer WHERE name = '名前'");
        CustomerEntity actual = new CustomerEntity();
        actual.setName((String) map.get("name"));
        actual.setEmail((String) map.get("email"));
        actual.setPassword((String) map.get("password"));
        actual.setAddress((String) map.get("address"));
        actual.setTel((String) map.get("tel"));
        actual.setRole((String) map.get("role"));
        expected.setPassword(actual.getPassword());
        jdbc.update("DELETE FROM customer WHERE name = '名前'");
        assertEquals(expected, actual);
    }

    @Test
    void testFindById()
    {
        CustomerEntity actual = dao.findById(999);
        CustomerEntity expected = new CustomerEntity();
        expected.setId(999);
        expected.setName("test");
        expected.setEmail("test@example.com");
        expected.setPassword("11111111");
        expected.setAddress("テスト市");
        expected.setTel("1122223333");
        expected.setRole("ROLE_TEST");
        assertEquals(expected, actual);
        actual = dao.findById(0);
        assertEquals(null, actual);
    }

    @Test
    void testFindByEmail()
    {
        CustomerEntity actual = dao.findByEmail("test@example.com");
        CustomerEntity expected = new CustomerEntity();
        expected.setId(999);
        expected.setName("test");
        expected.setEmail("test@example.com");
        expected.setPassword("11111111");
        expected.setAddress("テスト市");
        expected.setTel("1122223333");
        expected.setRole("ROLE_TEST");
        assertEquals(expected, actual);
        actual = dao.findByEmail("false@example.com");
        assertEquals(null, actual);
    }

    @Test
    void testUpdateById()
    {
        CustomerEntity expected = new CustomerEntity();
        expected.setId(999);
        expected.setName("update");
        expected.setEmail("updadte@example.com");
        expected.setPassword("22222222");
        expected.setAddress("更新市");
        expected.setRole("ROLE_UPDATE");
        expected.setTel("6677778888");
        dao.updateById(expected);
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM customer WHERE id = 999");
        CustomerEntity actual = new CustomerEntity();
        actual.setId((int) map.get("id"));
        actual.setName((String) map.get("name"));
        actual.setEmail((String) map.get("email"));
        actual.setPassword((String) map.get("password"));
        actual.setAddress((String) map.get("address"));
        actual.setRole((String) map.get("role"));
        actual.setTel((String) map.get("tel"));
        expected.setPassword(actual.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteById()
    {
        jdbc.update("INSERT INTO customer VALUES (1000, 'delete', 'delete@example.com', '44444444', '削除市', 'ROLE_DELETE', '3344445555')");
        dao.deleteById(1000);
        String actual;
        try
        {
            Map<String, Object> map = jdbc.queryForMap("SELECT * FROM customer WHERE id = 1000");
            actual = (String) map.get("name");
        }
        catch (EmptyResultDataAccessException e)
        {
            actual = null;
        }
        assertEquals(null, actual);
    }
}
