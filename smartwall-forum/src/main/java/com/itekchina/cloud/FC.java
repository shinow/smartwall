package com.itekchina.cloud;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("HELLO")
public interface FC {
	@RequestMapping(method = RequestMethod.GET, value = "/license/qiqiqiqi", consumes = "application/json")
	public License getLicense();
}
