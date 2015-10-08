package com.imconnect.core.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.imconnect.core.model.user.User;

@Component
public class UserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private MongoOperations operations;
	
	public Long countAllUsers() {
		return this.operations.count(null, User.class);
	}
}
