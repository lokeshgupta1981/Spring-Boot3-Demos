package com.howtodoinjava.app.customValidator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

  Long id;
  String firstName;
  String lastName;
  String email;
  Boolean active;

  Department department;
}
