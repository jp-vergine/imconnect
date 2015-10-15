package com.imconnect.core.repositories.user;

import com.imconnect.core.model.user.User;

public interface CustomUserRepository {

	public Long countAllUsers();
	
	public User findByPseudo(String pseudo);
	
	public User save(User user);
	
}
