package com.example.demo.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * メールアドレス二重登録防止用アノテーション。
 * @author tanakamasato
 * @since 2021/01/27
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicateValidator.class)
public @interface DuplicateCheck
{
    String message() default "その{0}は登録されています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
