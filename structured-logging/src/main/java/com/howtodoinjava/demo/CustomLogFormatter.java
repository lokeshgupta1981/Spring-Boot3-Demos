package com.howtodoinjava.demo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

public class CustomLogFormatter implements StructuredLogFormatter<ILoggingEvent> {

  @Override
  public String format(ILoggingEvent event) {

    // Use a StringBuilder for efficient string concatenation
    StringBuilder logBuilder = new StringBuilder();

    // Add basic log details
    logBuilder.append("time=").append(event.getTimeStamp())
      .append(",logger=").append(event.getLoggerName())
      .append(",level=").append(event.getLevel())
      .append(",message=\"").append(event.getFormattedMessage()).append("\"");

    // Iterate over key-value pairs and append as CSV
    if (event.getKeyValuePairs() != null) {
      event.getKeyValuePairs()
        .stream()
        .forEach((pair) -> {
          logBuilder.append(",").append(pair.key).append("=").append(pair.value);
        });
    }

    // Iterate over MDC map and append as CSV
    if (event.getMDCPropertyMap() != null) {
      event.getMDCPropertyMap()
        .entrySet()
        .stream()
        .forEach((entry) -> {
          logBuilder.append(",").append(entry.getKey()).append("=").append(entry.getValue());
        });
    }

    // Add a newline at the end of the log
    logBuilder.append("\n");

    return logBuilder.toString();
  }
}