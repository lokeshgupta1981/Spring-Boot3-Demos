package com.howtodoinjava;

import com.howtodoinjava.model.Employee;
import com.howtodoinjava.service.EmployeeService;
import javax.cache.Cache;
import javax.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private EmployeeService employeeService;

  @Override
  public void run(String... args) throws Exception {

    //This will hit the database
    employeeService.getEmployeeById(1L);

    //This will hit the cache - verify the message in console output
    employeeService.getEmployeeById(1L);
  }
}
