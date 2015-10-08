package com.imconnect.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@Profile("test")
@Configuration
public class MongoDbTestConfig extends MongoDbConfig {

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new Fongo("embedded-mongo").getMongo();
	}
}