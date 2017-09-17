package link.smartwall.cloud.forum.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
@ComponentScan
public class HelloController {
	@RequestMapping("/hello")
	public String index() {
		return "Hello World";
	}
}