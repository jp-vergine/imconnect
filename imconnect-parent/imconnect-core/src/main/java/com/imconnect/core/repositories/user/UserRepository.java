package com.imconnect.core.repositories.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.imconnect.core.model.user.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CustomUserRepository {


	//http://stackoverflow.com/questions/28746460/spring-pagingandsortingrepository-find-by-name
	//http://docs.spring.io/spring-data/jpa/docs/1.8.0.M1/reference/html/#repositories.query-methods.query-creation
	Page<User> findByPseudoLike(Pageable pageable, String pseudo);
	
}
