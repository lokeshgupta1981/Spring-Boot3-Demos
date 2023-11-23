package com.howtodoinjava.app.customValidator.model.validation;

import com.howtodoinjava.app.customValidator.model.Department;
import com.howtodoinjava.app.customValidator.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class TestEmployeeValidator {

  static EmployeeValidator employeeValidator;

  @BeforeAll
  static void setup() {
    employeeValidator = new EmployeeValidator(new DepartmentValidator());
  }

  @Test
  void validate_ValidInput_NoErrors() {
    // Set up a valid user
    Employee employee = Employee.builder().id(1L)
      .firstName("Lokesh").lastName("Gupta").email("admin@howtodoinjava.com")
      .department(Department.builder().id(2L).name("Finance").build()).build();

    Errors errors = new BeanPropertyBindingResult(employee, "employee");
    employeeValidator.validate(employee, errors);

    Assertions.assertFalse(errors.hasErrors());
  }

  @Test
  void validate_InvalidInput_HasErrors() {
    // Set up a valid user
    Employee employee = Employee.builder().id(1L)
      .firstName("A").lastName("B").email("C")
      .department(Department.builder().id(2L).name("HR").build()).build();

    Errors errors = new BeanPropertyBindingResult(employee, "employee");
    employeeValidator.validate(employee, errors);

    Assertions.assertTrue(errors.hasErrors());
    Assertions.assertEquals(3, errors.getErrorCount());
  }
}
