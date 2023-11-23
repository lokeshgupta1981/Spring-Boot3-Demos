package com.howtodoinjava.app.customValidator.model.validation;

import com.howtodoinjava.app.customValidator.model.Department;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DepartmentValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return Department.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    //ValidationUtils.rejectIfEmpty(errors, "id", ValidationErrorCodes.ERROR_CODE_EMPTY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", ValidationErrorCodes.ERROR_CODE_EMPTY);

    Department department = (Department) target;

    if(department.getName() != null && department.getName().length() < 3) {
      errors.rejectValue("name", ValidationErrorCodes.ERROR_CODE_SIZE);
    }
  }
}
