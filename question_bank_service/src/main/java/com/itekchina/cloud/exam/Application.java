package com.itekchina.cloud.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author <a herf="wangbo@infinitek.net">wangbo</a>
 * @version 1.0
 * @since 2017-09-21，15:19
 */

@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan("com.itekchina.cloud.exam")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
