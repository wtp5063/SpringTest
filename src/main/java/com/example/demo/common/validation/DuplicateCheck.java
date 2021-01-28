package com.example.demo.common.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

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
