package com.howtodoinjava.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StructuredLoggingApplication implements CommandLineRunner {

  final static Logger log = LoggerFactory.getLogger(StructuredLoggingApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(StructuredLoggingApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.trace("Trace log");
    log.debug("Debug log");

    log.info("Info log");
    log.warn("Hey, This is a warning!");
    log.error("Oops! We have an Error. OK");

    /*MDC.put("MyKey1", "MyValue1");
    MDC.put("MyKey2", "MyValue2");*/

    /*log.atInfo()
      .setMessage("Info log")
      .addKeyValue("MyKey1", "MyValue1")
      .addKeyValue("MyKey2", "MyValue2")
      .log();*/
  }
}
