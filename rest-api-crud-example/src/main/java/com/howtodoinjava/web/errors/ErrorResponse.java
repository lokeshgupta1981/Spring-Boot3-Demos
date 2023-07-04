package com.howtodoinjava.web.errors;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "error")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

  private String message;
  private List<String> details;
}
