package com.howtodoinjava.app.customValidator.model.validation;

import com.howtodoinjava.app.customValidator.model.Department;
import com.howtodoinjava.app.customValidator.model.Employee;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EmployeeValidator implements Validator {

  DepartmentValidator departmentValidator;

  public EmployeeValidator(DepartmentValidator departmentValidator) {
    if (departmentValidator == null) {
      throw new IllegalArgumentException("The supplied Validator is null.");
    }
    if (!departmentValidator.supports(Department.class)) {
      throw new IllegalArgumentException("The supplied Validator must support the Department instances.");
    }
    this.departmentValidator = departmentValidator;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Employee.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    //ValidationUtils.rejectIfEmpty(errors, "id", ValidationErrorCodes.ERROR_CODE_EMPTY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", ValidationErrorCodes.ERROR_CODE_EMPTY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", ValidationErrorCodes.ERROR_CODE_EMPTY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", ValidationErrorCodes.ERROR_CODE_EMPTY);

    Employee employee = (Employee) target;

    if (employee.getFirstName() != null && employee.getFirstName().length() < 3) {
      errors.rejectValue("firstName", ValidationErrorCodes.ERROR_CODE_SIZE);
    }

    if (employee.getLastName() != null && employee.getLastName().length() < 3) {
      errors.rejectValue("lastName", ValidationErrorCodes.ERROR_CODE_SIZE);
    }

    //1
    try {
      errors.pushNestedPath("department");
      ValidationUtils.invokeValidator(this.departmentValidator, employee.getDepartment(), errors);
    } finally {
      errors.popNestedPath();
    }

    //2
    /*try {
      SimpleErrors deptErrors = new SimpleErrors(employee.getDepartment());
      ValidationUtils.invokeValidator(this.departmentValidator, employee.getDepartment(), deptErrors);
      errors.addAllErrors(deptErrors);
    }
    catch (Exception e) {
      e.printStackTrace();
    }*/
  }
}
