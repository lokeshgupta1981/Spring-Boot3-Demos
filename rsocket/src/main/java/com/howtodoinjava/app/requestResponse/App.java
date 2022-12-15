package com.howtodoinjava.app.requestResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Autowired
  RSocketRequester.Builder requesterBuilder;

  @Override
  public void run(String... args) throws Exception {
    RSocketRequester rsocketRequester = requesterBuilder.tcp("localhost", 7000);

    rsocketRequester
        .route("greeting/{name}", "Lokesh")
        .data("Hello there!")
        .retrieveMono(String.class)
        .subscribe(response -> log.info("RSocket response : {}", response));
  }
}
