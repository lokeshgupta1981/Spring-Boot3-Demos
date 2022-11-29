package com.howtodoinjava;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Autowired
  ApplicationEventPublisher publisher;

  @Override
  public void run(String... args) throws Exception {
    Instant start = Instant.now();
    IntStream.rangeClosed(1, 10_000)
        .forEachOrdered(i ->
            publisher.publishEvent("Hello #" + i + " at:" + LocalDateTime.now())
        );

    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();

    log.info("=====Elapsed time : {} ========", timeElapsed);
  }
}
