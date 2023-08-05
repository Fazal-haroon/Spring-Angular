package com.example.springrestapi.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InRangeValidator implements ConstraintValidator<InRangeCustomAnnotation, Integer> {
    private int min;
    private int max;

    @Override
    public void initialize(InRangeCustomAnnotation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer == null || (integer >= min && integer <= max);
    }
}
