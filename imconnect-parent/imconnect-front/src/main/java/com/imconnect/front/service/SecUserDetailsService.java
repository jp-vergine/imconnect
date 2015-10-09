package com.imconnect.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.imconnect.core.model.user.User;
import com.imconnect.core.repositories.user.UserRepository;

@Component
public class SecUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
		
      User user = userRepository.findByPseudo(pseudo);
      if(user == null){
          throw new UsernameNotFoundException(pseudo);
      }else{
          UserDetails details = new SecUserDetails(user);
          return details;
      }
	}
}
