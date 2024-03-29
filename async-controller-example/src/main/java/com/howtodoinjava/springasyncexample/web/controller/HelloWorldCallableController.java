package com.howtodoinjava.springasyncexample.web.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

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
