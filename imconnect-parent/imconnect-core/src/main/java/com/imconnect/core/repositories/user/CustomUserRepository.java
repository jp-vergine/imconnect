package com.imconnect.core.repositories.user;

import com.imconnect.core.model.user.User;

public interface CustomUserRepository {

	Long countAllUsers();
	
	User findByPseudo(String pseudo);
}
