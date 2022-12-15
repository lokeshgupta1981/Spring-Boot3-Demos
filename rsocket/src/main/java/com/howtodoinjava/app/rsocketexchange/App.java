package com.howtodoinjava.app.rsocketexchange;

import com.howtodoinjava.app.rsocketexchange.declarativeClient.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;
import reactor.core.publisher.Mono;

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
    RSocketServiceProxyFactory factory = RSocketServiceProxyFactory.builder(rSocketRequester).build();

    MessageService service = factory.createClient(MessageService.class);

    Mono<String> response = service.sendMessage("Lokesh", Mono.just("Hello there!"));
    response.subscribe(message -> log.info("RSocket response : {}", message));
  }
}
