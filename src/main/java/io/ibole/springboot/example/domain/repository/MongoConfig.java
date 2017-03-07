package io.ibole.springboot.example.domain.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"io.ibole.springcloud.sample.domain.repository"})
public class MongoConfig {

}
