package com.howtodoinjava.app.config;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

  @Bean
  public WebClient webClient() {
    HttpAsyncClientBuilder clientBuilder = HttpAsyncClients.custom();
    //clientBuilder.setDefaultRequestConfig();
    CloseableHttpAsyncClient client = clientBuilder.build();
    ClientHttpConnector connector = new HttpComponentsClientHttpConnector(client);
    WebClient webClient = WebClient.builder().clientConnector(connector).build();
    return webClient;
  }
}
