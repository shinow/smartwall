package com.itekchina.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class License {
	private String name;

	@Value("${spring.data.mongodb.port}")
	private String value;

	public License() {
		System.out.println("\n\n\n\n");
		System.out.println(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
