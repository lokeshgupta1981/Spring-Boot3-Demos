package com.howtodoinjava.springasyncexample.demo2.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(HelloWorldCallableController.class)
public class HelloWorldCallableControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHelloWorldController() throws Exception {

    MvcResult mvcResult = mockMvc.perform(get("/testCallable"))
				.andExpect(request().asyncStarted())
        .andDo(MockMvcResultHandlers.log())
				.andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith("text/plain"))
        .andExpect(content().string("Hello World !!"));
  }
}
