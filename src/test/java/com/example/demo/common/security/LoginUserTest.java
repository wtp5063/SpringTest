package com.example.demo.common.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.CustomerEntity;

class LoginUserTest
{

    @BeforeAll
    static void setUpBeforeClass() throws Exception
    {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception
    {
    }

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @AfterEach
    void tearDown() throws Exception
    {
    }

    @Test
    void testGetEntity()
    {
        CustomerEntity expected = new CustomerEntity();
        expected.setEmail("email");
        expected.setPassword("password");
        expected.setRole("role");
        LoginUser loginUser = new LoginUser(expected);
        CustomerEntity actual = loginUser.getEntity();
        assertEquals(expected, actual);
    }

}
