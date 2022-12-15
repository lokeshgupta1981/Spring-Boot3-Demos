package com.howtodoinjava.app.fireAndForget.controller;

import com.howtodoinjava.app.fireAndForget.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class EventController {

  @MessageMapping("event")
  public Mono<Void> setAlert(Mono<Event> alertMono) {
    return alertMono
        .doOnNext(event ->
            log.info("Event Id '{}' occurred of type '{}' at '{}'",
                event.getId(),
                event.getType(),
                event.getTimestamp())
        )
        .thenEmpty(Mono.empty());
  }
}
