package com.example.demo.common.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.service.DuplicateValidatorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuplicateValidator implements ConstraintValidator<DuplicateCheck, String>
{
    private final DuplicateValidatorService service;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context)
    {
        if(service.findByEmail(email) == null) {
            return true;
        }
        return false;
    }

}
