package com.rabbit.second;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SecondApplication {

		public static void main(String[] args) {
				SpringApplication.run(SecondApplication.class, args);
		}

}

