package com.imconnect.core.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import com.imconnect.core.model.user.User;
import com.imconnect.core.repositories.util.SequenceRepository;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private MongoOperations operations;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	private static final String USER_SEQ_KEY = "user";
	
	/**
	 * Save user on database
	 */
	public User save(User user) {
		
		if(user.getId()==null)
			user.setId(sequenceRepository.getNextSequenceId(USER_SEQ_KEY));
		
		mongoTemplate.save(user);
		return user;
	}
	
	/**
	 * Count number of users
	 */
	public Long countAllUsers() {
		return this.operations.count(null, User.class);
	}

	/**
	 * Find user by pseudo
	 */
	public User findByPseudo(String pseudo) {
		User user = operations.findOne(new BasicQuery("{ pseudo : '"+ pseudo + "' }"), User.class);
		return user;
	}

//	/**
//	 * Find all user with pseudo like
//	 */
//	public Page<User> findByPseudo(PageRequest pageRequest, String pseudo) {
//		
//		MongodbQuery<User> countQuery = createQuery();
//		MongodbQuery<User> query = createQuery();
//
//		return new PageImpl(applyPagination(query, pageable).list(), pageable, countQuery.count());
//	}
}
