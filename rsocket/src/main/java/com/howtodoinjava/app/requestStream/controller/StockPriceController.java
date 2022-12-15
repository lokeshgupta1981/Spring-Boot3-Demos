package com.howtodoinjava.app.requestStream.controller;

import com.howtodoinjava.app.requestStream.model.StockPrice;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class StockPriceController {

  @MessageMapping("stock/{symbol}")
  public Flux<StockPrice> getStockPrice(@DestinationVariable("symbol") String symbol) {

    return Flux
        .interval(Duration.ofSeconds(1))
        .map(i -> {
          BigDecimal price = BigDecimal.valueOf(Math.random() * 10);
          return new StockPrice(symbol, price, Instant.now());
        });
  }
}
