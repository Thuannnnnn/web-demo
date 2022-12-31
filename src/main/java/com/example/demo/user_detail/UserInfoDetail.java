package com.example.demo.user_detail;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;

public class UserInfoDetail implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String email;
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public UserInfoDetail(User user) {
		id = user.getId();
		email = user.getEmail();
		username = user.getUsername();
		password = user.getPassword();
		authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
	}
	
	public String getUserId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
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
