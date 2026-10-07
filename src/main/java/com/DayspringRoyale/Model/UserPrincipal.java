package com.DayspringRoyale.Model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private User user;

	public UserPrincipal(User user) {
		this.user = user;
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRole().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
				.collect(Collectors.toSet());
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	

}
