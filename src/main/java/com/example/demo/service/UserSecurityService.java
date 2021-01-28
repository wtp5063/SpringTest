package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;
import com.example.demo.repository.CustomerDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService
{
    private final CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        CustomerEntity entity = customerDao.findByEmail(email);
        if(entity == null) {
            throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
        }
        LoginUser loginUser = new LoginUser(entity);
        return loginUser;
    }

}
