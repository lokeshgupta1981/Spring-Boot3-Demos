package com.howtodoinjava.app.fireAndForget;

import com.howtodoinjava.app.fireAndForget.model.Event;
import com.howtodoinjava.app.fireAndForget.model.Event.Type;
import java.time.Instant;
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
    RSocketRequester rSocketRequester = requesterBuilder.tcp("localhost", 7000);

    rSocketRequester
        .route("event")
        .data(new Event(11L, Type.ERROR, Instant.now()))
        .send()
        .subscribe();
  }
}
