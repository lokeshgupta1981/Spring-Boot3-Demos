package com.howtodoinjava.app.requestStream.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockPrice {

  private String symbol;
  private BigDecimal price;
  private Instant timestamp;
}
