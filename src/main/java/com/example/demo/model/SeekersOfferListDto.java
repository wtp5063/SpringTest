package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 求職者向け応募一覧画面用Dto class。
 * @author tanakamasato
 * @since 2021/01/30
 */
@Data
public class SeekersOfferListDto
{
    /**
     * offer(customerテーブルとjobテーブルの中間テーブル)テーブルのプライマリキー。
     */
    private int id;

    /**
     * タイトル。
     */
    private String title;

    /**
     * 応募日時。
     */
    private Timestamp datetime;

    /**
     * 会社名。
     */
    private String Company;

    /**
     * 給与(最低)。
     */
    private int min_salary;

    /**
     * 給与(最高)。
     */
    private int max_salary;
}
