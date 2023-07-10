package com.howtodoinjava.tasks;

import java.time.LocalDateTime;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log
public class Task {

  @Scheduled(initialDelay = 1000, fixedRate = 10000)
  public void run() {
    log.info("Current time is :: " + LocalDateTime.now());
  }

  @Scheduled(fixedRate = 10000)
  @Async
  public void taskWithConcurrentExecutions() {
    log.info("Current time is :: " + LocalDateTime.now());
  }
}
