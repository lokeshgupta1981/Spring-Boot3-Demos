package com.howtodoinjava.app.bidirectional.controller;

import com.howtodoinjava.app.bidirectional.model.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class LoanCheckController {

  @MessageMapping("check-loan-eligibility")
  public Flux<Boolean> calculate(Flux<LoanDetails> loanDetails) {
    return loanDetails
        .doOnNext(ld -> log.info("Calculating eligibility:  {}", ld))
        .map(ld -> {
          return ld.getRate() > 9;
        });
  }
}
