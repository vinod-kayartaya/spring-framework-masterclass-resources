package co.vinod.service;

public class GreetingService {

	public String getMessage(String name) {
		if(name==null) {
			name = "Web MVC";
		}
		return "Hello, " + name + "!";
	}
}
