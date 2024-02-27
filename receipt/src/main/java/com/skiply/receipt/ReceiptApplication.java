package com.skiply.receipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReceiptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptApplication.class, args);
	}

}
