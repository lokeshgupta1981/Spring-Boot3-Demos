package com.howtodoinjava.app.web;

import com.howtodoinjava.app.model.Employee;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;

@RestController
public class EmployeeController {

  private final WebClient webClient;

  public EmployeeController(WebClient webClient) {
    this.webClient = webClient;
  }

  public void createEmployee(){

    Employee newEmployee = new Employee(1L, "Lokesh Gupta", "Active");

    webClient.post()
        .uri("/employees")
        .bodyValue(BodyInserters.fromValue(newEmployee))
        .retrieve()
        .toBodilessEntity()
        .subscribe(
            responseEntity -> {
              // Handle success response here
              HttpStatusCode status = responseEntity.getStatusCode();
              URI location = responseEntity.getHeaders().getLocation();
              // handle response as necessary
            },
            error -> {
              // Handle the error here
              if (error instanceof WebClientResponseException ex) {
                HttpStatusCode status = ex.getStatusCode();
                System.out.println("Error Status Code: " + status.value());
                //...
              } else {
                // Handle other types of errors
                System.err.println("An unexpected error occurred: " + error.getMessage());
              }
            }
        );
  }
}
