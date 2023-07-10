package com.howtodoinjava.service;

import com.howtodoinjava.model.Employee;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Log
public class EmployeeService
{
  static Map<Long, Employee> db = new HashMap<>();

  static {
    db.put(1L, new Employee(1L, "Alex", "Gussin"));
    db.put(2L, new Employee(2L, "Brian", "Schultz"));
  }

  @Cacheable(cacheNames="employeeCache", key="#id")
  public Employee getEmployeeById(Long id) {
    log.info("Getting employee from DB");
    return db.get(id);
  }
}