package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * offer(customerとjobの中間テーブル)テーブルのEntity class。
 * @author tanakamasato
 * @since 2021/01/29
 */
@Data
public class OfferEntity
{
    /**
     * offer(customerとjobの中間テーブル)テーブルのプライマリキー。
     */
    private int id;

    /**
     * customer(ユーザー情報)テーブルのプライマリキー。
     */
    private int customer_id;

    /**
     * job(求人情報)テーブルのプライマリキー。
     */
    private int job_id;

    /**
     * 申込み日時。
     */
    private Timestamp datetime;
}
