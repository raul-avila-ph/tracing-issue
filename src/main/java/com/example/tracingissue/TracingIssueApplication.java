package com.example.tracingissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = MyFeignClient.class)
public class TracingIssueApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracingIssueApplication.class, args);
	}

}
