package com.howtodoinjava.app.config;


import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class DynamicHeaderFilter implements ExchangeFilterFunction {

  @Override
  public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction nextFilter) {

    // Create a new ClientRequest with the additional headers
    ClientRequest modifiedRequest = ClientRequest
        .from(clientRequest)
        .header("X-REQUEST-TIMESTAMP", LocalDateTime.now().toString())
        .build();

    return nextFilter.exchange(modifiedRequest);
  }
}
