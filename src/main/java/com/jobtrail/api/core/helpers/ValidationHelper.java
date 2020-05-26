package com.jobtrail.api.core.helpers;

import com.jobtrail.api.models.ValidationResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationHelper {
    public static <T> ValidationResult validate(T model, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);

        List<String> errors = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return new ValidationResult(violations.isEmpty(), errors);
    }
}
