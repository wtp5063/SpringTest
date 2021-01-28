package com.example.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.common.validation.DuplicateCheck;
import com.example.demo.common.validation.InsertGroup;
import com.example.demo.common.validation.order.ValidGroup1;
import com.example.demo.common.validation.order.ValidGroup2;

import lombok.Data;

/**
 * customer(ユーザー情報)テーブルのEntity class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Data
public class CustomerEntity
{
    /**
     * customer(ユーザー情報)テーブルのプライマリキー。
     */
    private int id;

    /**
     * 名前。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    private String name;

    /**
     * メールアドレス。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    @DuplicateCheck(groups = InsertGroup.class)
    private String email;

    /**
     * パスワード。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    private String password;

    /**
     * 住所。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    private String address;

    /**
     * 権限(採用者はROLE_EMPLOYER、求職者はROLE_SEEKER)
     */
    private String role;

    /**
     * 電話番号
     */
    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^0\\d{9,10}$", groups = ValidGroup2.class)
    private String tel;
}
