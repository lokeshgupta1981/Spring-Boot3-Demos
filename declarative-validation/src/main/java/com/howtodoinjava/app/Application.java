package com.howtodoinjava.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

@SpringBootApplication
public class Application implements CommandLineRunner {

  MessageSource messageSource;

  public Application(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {
  }
}