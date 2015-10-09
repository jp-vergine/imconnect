package com.imconnect.core.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.imconnect.core.model.user.User;

@Component
public class UserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private MongoOperations operations;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Long countAllUsers() {
		return this.operations.count(null, User.class);
	}

	public User findByPseudo(String pseudo) {
		User user = mongoTemplate.findOne(new BasicQuery("{ pseudo : '"+ pseudo + "' }"), User.class);
		return user;
	}
}
