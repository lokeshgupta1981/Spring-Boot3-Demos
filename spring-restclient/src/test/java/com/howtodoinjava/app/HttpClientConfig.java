package com.howtodoinjava.app;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * - Supports both HTTP and HTTPS - Uses a connection pool to re-use connections and save overhead
 * of creating connections. - Has a custom connection keep-alive strategy (to apply a default
 * keep-alive if one isn't specified) - Starts an idle connection monitor to continuously clean up
 * stale connections.
 */
@Configuration
@EnableScheduling
public class HttpClientConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);
  private static final int CONNECT_TIMEOUT = 30000;
  private static final int REQUEST_TIMEOUT = 30000;
  private static final int MAX_TOTAL_CONNECTIONS = 50;

  @Bean
  public PoolingHttpClientConnectionManager poolingConnectionManager() {
    SSLContextBuilder builder = new SSLContextBuilder();
    try {
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
    } catch (NoSuchAlgorithmException | KeyStoreException e) {
      LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(),
          e);
    }
    SSLConnectionSocketFactory sslsf = null;
    try {
      sslsf = new SSLConnectionSocketFactory(builder.build());
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(),
          e);
    }
    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
        .<ConnectionSocketFactory>create().register("https", sslsf)
        .register("http", new PlainConnectionSocketFactory())
        .build();
    PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(
        socketFactoryRegistry);
    poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
    return poolingConnectionManager;
  }

  @Bean
  public CloseableHttpClient httpClient() {
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(Timeout.of(Duration.ofMillis(REQUEST_TIMEOUT)))
        .setResponseTimeout(Timeout.of(Duration.ofMillis(CONNECT_TIMEOUT)))
        .build();
    return HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .setConnectionManager(poolingConnectionManager())
        .build();
  }
}
