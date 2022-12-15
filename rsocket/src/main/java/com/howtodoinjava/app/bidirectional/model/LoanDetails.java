package com.howtodoinjava.app.bidirectional.model;

import java.time.Period;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanDetails {

  private double amount;
  private float rate;
  private int years;
}
