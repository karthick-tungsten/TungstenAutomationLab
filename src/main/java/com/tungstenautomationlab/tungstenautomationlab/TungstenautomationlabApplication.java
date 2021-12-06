package com.tungstenautomationlab.tungstenautomationlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TungstenautomationlabApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TungstenautomationlabApplication.class, args);
	}
	
	@Override
    	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        	return application.sources(TungstenautomationlabApplication.class);
    	}  

}
