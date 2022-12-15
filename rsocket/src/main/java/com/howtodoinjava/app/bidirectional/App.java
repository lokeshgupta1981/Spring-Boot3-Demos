package com.howtodoinjava.app.bidirectional;

import com.howtodoinjava.app.bidirectional.model.LoanDetails;
import com.howtodoinjava.app.fireAndForget.model.Event;
import com.howtodoinjava.app.fireAndForget.model.Event.Type;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

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

    Flux<LoanDetails> lonaDetailsFlux =
        Flux.fromArray(new LoanDetails[]{
                new LoanDetails(100, 5, 1),
                new LoanDetails(200, 7, 1),
                new LoanDetails(300, 10, 1),
                new LoanDetails(400, 8, 1),
                new LoanDetails(600, 11, 1)
            })
            .delayElements(Duration.ofSeconds(2));

    rSocketRequester
        .route("check-loan-eligibility")
        .data(lonaDetailsFlux)
        .retrieveFlux(Boolean.class)
        .subscribe(result ->
            log.info("Loan eligibility : {}", result));
  }
}
