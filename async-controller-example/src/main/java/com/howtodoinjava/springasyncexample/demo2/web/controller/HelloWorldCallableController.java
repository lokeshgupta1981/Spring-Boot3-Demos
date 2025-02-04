package com.howtodoinjava.springasyncexample.demo2.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Callable;

@RestController
public class HelloWorldCallableController 
{
	@GetMapping(value = "/testCallable")
	public Callable<String> echoHelloWorld() 
	{
		return () -> {
			Thread.sleep(5000);
			return "Hello World !!";
		};
	}
}
