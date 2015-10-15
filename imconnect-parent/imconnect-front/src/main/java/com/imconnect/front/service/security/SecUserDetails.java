package com.imconnect.front.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.imconnect.core.model.user.User;

public class SecUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	
	private User user;
	
	public SecUserDetails(User user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//Test
		GrantedAuthority newrole = new SimpleGrantedAuthority("ROLE_ADMIN");
		ArrayList<GrantedAuthority> temp = new ArrayList<GrantedAuthority>();
	    temp.add(newrole); 
	    
	    return Collections.unmodifiableList(temp);
	}

	@Override
	public String getPassword() {
		// Test
		return "1234";
	}

	@Override
	public String getUsername() {
		return user.getPseudo();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
