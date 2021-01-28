package com.example.demo.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.common.validation.order.ValidGroup1;
import com.example.demo.common.validation.order.ValidGroup2;

import lombok.Data;

/**
 * job(求人情報)テーブルのEntity class。
 * @author tanakamasato
 * @since 2021/01/26
 */
@Data
public class JobEntity
{
    /**
     * job(求人情報)テーブルのプライマリキー。
     */
    private int id;


    /**
     * customer(ユーザー情報)テーブルのプライマリキー
     */
    private int customer_id;

    /**
     * 求人情報のタイトル
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    private String title;

    /**
     * 会社名。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 255, groups = ValidGroup2.class)
    private String company;

    /**
     * 最終更新日時。
     */
    private Timestamp datetime;

    /**
     * 求人情報の内容。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Size(min = 0, max = 5000, groups = ValidGroup2.class)
    private String description;

    /**
     * 給与(最低)。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "\\d{1,7}", groups = ValidGroup2.class)
    private String min_salary;

    /**
     * 給与(最高)。
     */
    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "\\d{1,7}", groups = ValidGroup2.class)
    private String max_salary;
}
