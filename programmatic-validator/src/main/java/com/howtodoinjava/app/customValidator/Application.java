package com.howtodoinjava.app.customValidator;

import com.howtodoinjava.app.customValidator.model.Department;
import com.howtodoinjava.app.customValidator.model.Employee;
import com.howtodoinjava.app.customValidator.model.validation.DepartmentValidator;
import com.howtodoinjava.app.customValidator.model.validation.EmployeeValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Locale;

@SpringBootApplication
public class Application implements CommandLineRunner {

  MessageSource messageSource;

  public Application(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {

    Department department = Department.builder().id(1L).build();
    Employee employee = Employee.builder().id(2L).department(department).build();

    EmployeeValidator employeeValidator = new EmployeeValidator(new DepartmentValidator());

    //1
    Errors errors = new BeanPropertyBindingResult(employee, "employee");
    employeeValidator.validate(employee, errors);

    if (!errors.hasErrors()) {
      System.out.println("Object is valid.");
    } else {
      System.out.println(errors);
      for (FieldError error : errors.getFieldErrors()) {
        System.out.println(error.getField());
        System.out.println(error.getRejectedValue());
        System.out.println(error.getCode());
        System.out.println(messageSource.getMessage(error.getCode(),new Object[]{error.getField()}, Locale.ENGLISH));
      }
    }

    //2
    //employeeValidator.validateObject(employee).failOnError(IllegalArgumentException::new);
  }
}