package com.example.demo.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.example.demo.model.CustomerEntity;


public class LoginUser extends User
{
    private CustomerEntity entity;

    public LoginUser(CustomerEntity entity)
    {
        super(entity.getEmail(), entity.getPassword(), authorities(entity.getRole()));
        // TODO 自動生成されたコンストラクター・スタブ
        this.entity = entity;
    }

    private static Collection<? extends GrantedAuthority> authorities(String auth)
    {
        if (auth.equals("ROLE_EMPLOYER"))
        {
            return AuthorityUtils.createAuthorityList(auth);
        }
        return AuthorityUtils.createAuthorityList("ROLE_SEEKER");
    }

    public CustomerEntity getEntity()
    {
        return entity;
    }
}
