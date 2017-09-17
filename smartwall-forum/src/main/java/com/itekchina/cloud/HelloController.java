package com.itekchina.cloud;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * Hello world!
 *
 */
@RestController
public class HelloController {
	@Autowired
	private FC fc;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/hello")
	public String index() {
		// ServiceInstance instance = client.getInstances("HELLO").get(0);
		// if (instance == null) {
		// return "";
		// }
		//
		// String serviceUri = String.format("%s/license/qiqiqiqi",
		// instance.getUri());
		// System.out.println(serviceUri);
		//
		// ResponseEntity<License> restExchange = new
		// RestTemplate().exchange(serviceUri, HttpMethod.GET, null,
		// License.class);
		// System.out.println(restExchange.getBody());
		// System.out.println(fc.getLicense());

		return "Hello World";
	}

	@Value("${spring.data.mongodb.port}")
	private String port;

	@HystrixCommand(fallbackMethod = "dd")
	@RequestMapping(value = "/license/{name}", method = RequestMethod.GET)
	public License getLicence(@PathVariable("name") String name) {
		License c = new License();
		c.setName(name);
		c.setValue(port);
		// c.setValue("that is very good");

		System.out.println(port);
		return c;
	}

	public License dd(String name) {
		License c = new License();
		c.setName("KISS");

		return c;
	}
}