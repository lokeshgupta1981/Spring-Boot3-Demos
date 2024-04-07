package com.howtodoinjava.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.web.UserClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfig {

  @Autowired
  DynamicHeaderFilter dynamicHeaderFilter;

  @Bean
  WebClient webClient(/*ObjectMapper objectMapper*/) {
    return WebClient.builder()
        .exchangeStrategies(ExchangeStrategies.builder().codecs(c ->
            c.defaultCodecs().enableLoggingRequestDetails(true)).build()
        )
        .defaultHeaders(header -> header.setBasicAuth("username", "password"))
        .filter(dynamicHeaderFilter)
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .build();
  }

  @SneakyThrows
  @Bean
  UserClient postClient(WebClient webClient) {
    HttpServiceProxyFactory httpServiceProxyFactory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
            .build();
    return httpServiceProxyFactory.createClient(UserClient.class);
  }
}
