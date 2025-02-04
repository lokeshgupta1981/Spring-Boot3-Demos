package com.howtodoinjava.springasyncexample.demo2.web.model;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSet {

  private BigInteger id;
  private String name;
}
