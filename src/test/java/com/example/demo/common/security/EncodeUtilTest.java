package com.example.demo.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class EncodeUtilTest
{

    @Test
    void testPasswordEncoder()
    {
        PasswordEncoder actual = EncodeUtil.passwordEncoder();
        assert(actual != null);
        assert(actual instanceof BCryptPasswordEncoder);
        }

}
