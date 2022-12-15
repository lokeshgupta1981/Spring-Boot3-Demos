package com.howtodoinjava.app.requestStream;

import com.howtodoinjava.app.requestStream.model.StockPrice;
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

    String stockSymbol = "TESLA";

    rSocketRequester
        .route("stock/{symbol}", stockSymbol)
        .retrieveFlux(StockPrice.class)
        .doOnNext(stockPrice ->
            log.info(
                "Price of {} : {} (at {})",
                stockPrice.getSymbol(),
                stockPrice.getPrice(),
                stockPrice.getTimestamp())
        )
        .subscribe();
  }
}
