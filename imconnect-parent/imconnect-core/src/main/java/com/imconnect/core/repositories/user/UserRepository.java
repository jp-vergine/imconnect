package com.imconnect.core.repositories.user;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.imconnect.core.model.user.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CustomUserRepository {

}
