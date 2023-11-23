package com.howtodoinjava.app.web;

import com.howtodoinjava.app.exceptions.ErrorResponse;
import com.howtodoinjava.app.exceptions.RecordNotFoundException;
import com.howtodoinjava.app.model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  /*@GetMapping("/registration")
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
  }*/

  @PostMapping
  public ResponseEntity<?> showRegistrationForm(@Valid @RequestBody Employee employee) {
    //TODO: Save the employee
    return ResponseEntity.ok(employee);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
    //TODO: Fetch teh employee from the database
    if (id <= 0) {
      throw new RecordNotFoundException("Invalid employee id : " + id);
    }
    Employee employee = Employee.builder().id(id).firstName("Lokesh").lastName("Gupta").email("Admin@howtodoinjava.com").build();
    return ResponseEntity.ok(employee);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

    List<String> details = new ArrayList<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      details.add(error.getDefaultMessage());
    }
    ErrorResponse error = new ErrorResponse("Validation Failed", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
}
