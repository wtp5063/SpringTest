package com.example.demo.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.service.DuplicateValidatorService;

import lombok.RequiredArgsConstructor;

/**
 * メールアドレス二重登録防止バリデーション用class。
 * @author tanakamasato
 * @since 2021/01/27
 */
@RequiredArgsConstructor
public class DuplicateValidator implements ConstraintValidator<DuplicateCheck, String>
{
    /**
     * 専用Service class。
     */
    private final DuplicateValidatorService service;

    /**
     * customer(ユーザー情報テーブル)にデータがあるか確認する。
     * @param email メールアドレス。
     * @return データが無かった場合：true、データがあった場合：false。
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context)
    {
        if(service.findByEmail(email) == null) {
            return true;
        }
        return false;
    }

}
