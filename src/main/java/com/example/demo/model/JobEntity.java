package com.example.demo.model;

import java.sql.Timestamp;

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
    private String title;

    /**
     * 会社名。
     */
    private String company;

    /**
     * 最終更新日時。
     */
    private Timestamp datetime;

    /**
     * 求人情報の内容。
     */
    private String description;

    /**
     * 給与(最低)。
     */
    private int min_salary;

    /**
     * 給与(最高)。
     */
    private int max_salary;
}
