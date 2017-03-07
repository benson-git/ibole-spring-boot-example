package io.ibole.springboot.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = {"io.ibole.springboot.example.domain.repository"})
public class ExampleServer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ExampleServer.class, args);
	}

}
