package com.imconnect.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Profile("default")
@Configuration
@EnableMongoRepositories(basePackages = "com.imconnect.core.repositories")
public class MongoDbConfig extends AbstractMongoConfiguration {

	@Value("${mongo.url}")
	private String url;

	@Value("${mongo.db}")
	private String databaseName;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(url);
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.imconnect.core.model";
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		MongoClientURI uri = new MongoClientURI(url + "/" + databaseName);
	    return new SimpleMongoDbFactory(uri);
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	    return new MongoTemplate(mongoDbFactory(), mongoConverter());
	}
	
	@Bean
	public MappingMongoConverter mongoConverter() throws Exception {
	    MongoMappingContext mappingContext = new MongoMappingContext();
	    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
	    MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
	    mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
//	    mongoConverter.setCustomConversions(customConversions());
	    return mongoConverter;
	}
	
//	@Bean
//	public CustomConversions customConversions() {
//	    List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
//	    converters.add(new TimeZoneReadConverter());
//	    converters.add(new TimeZoneReadConverter());
//	    return new CustomConversions(converters);
//	}
	
	//mongod --dbpath "C:\dev\program\mongodb3.0\imconnect"
}