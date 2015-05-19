package com.zzy.base.service;

import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloServiceImpl implements  HelloService{
	public void sayHello(){
		System.out.println("hello");
	}
}
