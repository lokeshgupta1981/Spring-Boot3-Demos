package com.howtodoinjava.config;

import java.util.concurrent.Executors;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

  @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
  public AsyncTaskExecutor asyncTaskExecutor() {
    //TODO: Enable in Java 20
    //return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    return new TaskExecutorAdapter(Executors.newSingleThreadExecutor());
  }

  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    return protocolHandler -> {
      //TODO: Enable in Java 20
      //protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    };
  }
}
