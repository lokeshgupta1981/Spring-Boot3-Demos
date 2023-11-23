package com.howtodoinjava.app.customValidator.web;

import com.howtodoinjava.app.customValidator.model.Employee;
import com.howtodoinjava.app.customValidator.model.validation.DepartmentValidator;
import com.howtodoinjava.app.customValidator.model.validation.EmployeeValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

    @Controller
    @RequestMapping("/employees")
    public class EmployeeController {

      @InitBinder
      protected void initBinder(WebDataBinder binder) {
        // Injecting Programmatic Validators
        binder.setValidator(new EmployeeValidator(new DepartmentValidator()));
      }

      @GetMapping("/registration")
      public String showRegistrationForm(Model model) {
        model.addAttribute("employee", Employee.builder().build());
        return "employee-registration-form";
      }

      @PostMapping("/processRegistration")
      public String processRegistration(
        @Validated @ModelAttribute("employee") Employee employee,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
          return "employee-registration-form";
        }

        // Logic for handling a successful form submission
        // Typically involving database operations, authentication, etc.

        return "employee-registration-confirmation"; // Redirect to a success page
  }
}
