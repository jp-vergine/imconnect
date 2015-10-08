package com.imconnect.core.repositories.user;

import org.springframework.data.repository.CrudRepository;

import com.imconnect.core.model.user.User;

public interface UserRepository extends CrudRepository<User, Long>, CustomUserRepository {

}
