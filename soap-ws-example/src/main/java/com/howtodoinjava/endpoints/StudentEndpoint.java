package com.howtodoinjava.endpoints;

import com.howtodoinjava.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.howtodoinjava.xml.school.StudentDetailsRequest;
import com.howtodoinjava.xml.school.StudentDetailsResponse;

@Endpoint
public class StudentEndpoint
{
  private static final String NAMESPACE_URI = "http://www.howtodoinjava.com/xml/school";

  private StudentRepository StudentRepository;

  @Autowired
  public StudentEndpoint(StudentRepository StudentRepository) {
    this.StudentRepository = StudentRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
  @ResponsePayload
  public StudentDetailsResponse getStudent(@RequestPayload StudentDetailsRequest request) {
    StudentDetailsResponse response = new StudentDetailsResponse();
    response.setStudent(StudentRepository.findStudent(request.getName()));

    return response;
  }
}
