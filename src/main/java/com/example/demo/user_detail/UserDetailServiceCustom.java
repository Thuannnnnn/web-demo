package com.example.demo.user_detail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserDetailServiceCustom implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailServiceCustom(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("Không tìm người dùng bởi email: " + email);
		}
		return new UserInfoDetail(user);
	}

}
