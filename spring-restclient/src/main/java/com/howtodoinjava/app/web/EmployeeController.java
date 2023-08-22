package com.howtodoinjava.app.web;

import com.howtodoinjava.app.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {

  @Autowired
  WebClient webClient;

  public void getEmployee(){


  }
}
