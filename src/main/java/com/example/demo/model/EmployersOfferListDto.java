package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 採用担当者向け応募一覧画面用Dto class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Data
public class EmployersOfferListDto
{
    /**
     *  offer(customerテーブルとjobテーブルの中間テーブル)のプライマリキー。
     */
    private int id;

    /**
     * 応募日時。
     */
    private Timestamp datetime;

    /**
     * 求人のタイトル。
     */
    private String title;

    /**
     * 応募者の名前
     */
    private String name;

    /**
     * 応募者のメールアドレス。
     */
    private String email;

    /**
     * 応募者の住所。
     */
    private String address;

    /**
     * 応募者の電話番号。
     */
    private String tel;
}
