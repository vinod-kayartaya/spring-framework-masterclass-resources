package co.vinod.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping("/hello-mvc")
	public String sayHello() {
		System.out.println("HelloController.sayHello() called");
		return "hello";
	}
}
