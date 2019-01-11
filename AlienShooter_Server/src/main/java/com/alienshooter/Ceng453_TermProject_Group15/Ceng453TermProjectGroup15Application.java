package com.alienshooter.Ceng453_TermProject_Group15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ceng453TermProjectGroup15Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Ceng453TermProjectGroup15Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Ceng453TermProjectGroup15Application.class);
	}
}
