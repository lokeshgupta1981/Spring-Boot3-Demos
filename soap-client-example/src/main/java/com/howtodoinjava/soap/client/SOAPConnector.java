package com.howtodoinjava.soap.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SOAPConnector extends WebServiceGatewaySupport {

  public Object callWebService(String url, Object request){
    return getWebServiceTemplate().marshalSendAndReceive(url, request);
  }
}
