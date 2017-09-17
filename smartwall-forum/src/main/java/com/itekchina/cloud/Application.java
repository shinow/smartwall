package com.itekchina.cloud;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableBinding(Source.class)
//@RestController
//@EnableAuthorizationServer
public class Application {
//	@RequestMapping(value = { "/user" }, produces = "application/json")
//	public Map<String, Object> user(OAuth2Authentication user) {
//		Map<String, Object> userInfo = new HashMap<>();
//
//		System.out.println(user);
//		userInfo.put("user", user.getUserAuthentication().getPrincipal());
//		userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
//
//		return userInfo;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
