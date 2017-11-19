package link.smartwall.cloud.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import link.smartwall.cloud.forum.domain.Attach;
import link.smartwall.cloud.forum.service.ForumService;

/**
 * Hello world!
 *
 */
@RestController
@ComponentScan
public class HelloController {
	@Autowired
	private ForumService service;

	@RequestMapping("/hello")
	public String index(Attach attach) {
		service.insert(attach);

		return "Hello World";
	}
}