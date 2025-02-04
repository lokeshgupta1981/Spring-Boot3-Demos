package com.howtodoinjava.springasyncexample.demo1;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AsyncDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(AsyncDemoApplication.class, args);
  }
}

@Configuration
@EnableAsync
class AsyncConfig extends AsyncConfigurerSupport {

  @Bean(name = "asyncExecutor")
  public TaskExecutor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(3);
    executor.setMaxPoolSize(3);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new AsyncExceptionHandler();
  }
}

class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(AsyncExceptionHandler.class);

  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    logger.error("Unexpected asynchronous exception at : {}.{}",
      method.getDeclaringClass().getName(), method.getName(), ex);
  }
}

@Service
class AsyncService {

  private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

  @Async("asyncExecutor")
  public CompletableFuture<String> getEmployeeName() throws InterruptedException {
    log.info("Fetching Employee Name using RestClient/WebClient API...");
    Thread.sleep(1000);
    return CompletableFuture.completedFuture("John Doe");
  }

  @Async("asyncExecutor")
  public CompletableFuture<String> getEmployeeAddress() throws InterruptedException {
    log.info("Fetching Employee Address using RestClient/WebClient API...");
    Thread.sleep(1000);
    return CompletableFuture.completedFuture("123 Main St, Cityville");
  }

  @Async("asyncExecutor")
  public CompletableFuture<String> getEmployeePhone() throws InterruptedException {
    log.info("Fetching Employee Phone using RestClient/WebClient API...");
    Thread.sleep(1000);
    return CompletableFuture.completedFuture("+123456789");
  }
}

@RestController
@RequestMapping("/api")
class AsyncController {

  private final AsyncService asyncService;
  private static final Logger log = LoggerFactory.getLogger(AsyncController.class);

  public AsyncController(AsyncService asyncService) {
    this.asyncService = asyncService;
  }

  @GetMapping("/testAsync")
  public String testAsync() throws InterruptedException, ExecutionException {
    log.info("Starting Async Calls");

    CompletableFuture<String> nameFuture = asyncService.getEmployeeName();
    CompletableFuture<String> addressFuture = asyncService.getEmployeeAddress();
    CompletableFuture<String> phoneFuture = asyncService.getEmployeePhone();

    CompletableFuture.allOf(nameFuture, addressFuture, phoneFuture).join();

    String result = "Name: " + nameFuture.get() + ", Address: " + addressFuture.get() + ", Phone: "
      + phoneFuture.get();
    log.info("All async calls are completed. The combined result is : {}", result);
    return result;
  }
}

