package com.howtodoinjava.webclient.demo.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  private final WebClient webClient = WebClient.builder()
      .baseUrl("https://reqres.in/")
      .build();

  @Override
  public void run(String... args) throws Exception {

    ResponseEntity<User> userEntity = webClient
        .get()
        .uri("/api/users/{id}", 123)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(User.class)   // Fetch response entity
        .block();

    System.out.println(userEntity);

    User user = webClient
        .get()
        .uri("/api/users/{id}", 123)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException("Client Error: " + errorBody))))
        .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException("Server Error: " + errorBody))))
        .bodyToMono(User.class)   // Fetch response entity
        .doOnError(throwable -> log.warn("Error when issuing request :: ", throwable))  //Error handling
        .block();

    System.out.println(user);

    User user1 = webClient
        .get()
        .uri("/api/users/{id}", 123)  // Path parameter
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
          if (clientResponse.statusCode() == HttpStatus.BAD_REQUEST) {
            return clientResponse.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new BadRequestException("Bad Request: " + body)));
          }
          return Mono.error(new RuntimeException("Client error occurred"));
        })
        .bodyToMono(User.class)
        .doOnError(WebClientResponseException.class, e -> {
          ProblemDetail problemDetail = createProblemDetail(e.getStatusCode(), e.getResponseBodyAsString());
          System.err.println("Problem Detail: " + problemDetail);
        })
        .block(); // Blocking for synchronous call

    System.out.println(user1);

    Mono<User> userMono = getUserById(1, "id,name");
    System.out.println(userMono.block());
  }

  private ProblemDetail createProblemDetail(HttpStatusCode status, String errorBody) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, errorBody);
    problemDetail.setTitle("API Error");
    problemDetail.setType(URI.create("https://api.example.com/docs/errors"));
    problemDetail.setInstance(URI.create("/api/users"));
    return problemDetail;
  }

  public Mono<User> getUserById(int id, String includeFields) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/users/{id}")
            .queryParam("includeFields", includeFields)
            .build(id))
        .header(HttpHeaders.AUTHORIZATION, "Bearer your-token")  // Add headers
        .cookie("sessionId", "your-session-id")  // Add cookies
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Client Error: " + body))))
        .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Server Error: " + body))))
        .bodyToMono(User.class)  // Handle response and map to User class
        .doOnError(WebClientResponseException.class, e -> {
          // Handle error and log it
          System.err.println("Error occurred: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        });
  }
}

class BadRequestException extends Exception {
  public BadRequestException(String message) {
    super(message);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {

  private int id;
  private String firstName;
  private String lastName;
  private String email;
}
