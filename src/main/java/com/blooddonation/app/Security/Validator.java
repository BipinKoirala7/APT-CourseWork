package com.blooddonation.app.Security;

import com.blooddonation.app.Exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import java.util.Set;


public class Validator {

  private final jakarta.validation.Validator validator;

  public Validator() {
    this.validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  public <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ValidationException(violations.iterator().next().getMessage());
    }
  }
}
