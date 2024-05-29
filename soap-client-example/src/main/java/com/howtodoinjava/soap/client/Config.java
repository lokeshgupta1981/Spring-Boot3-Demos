package com.howtodoinjava.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Config {
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this is the package name specified in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.example.howtodoinjava.schemas.school");
    return marshaller;
  }

  @Bean
  public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
    SOAPConnector client = new SOAPConnector();
    client.setDefaultUri("http://localhost:8080/service/student-details");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
