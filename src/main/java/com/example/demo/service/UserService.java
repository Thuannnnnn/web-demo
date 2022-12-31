package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.MapperUtils;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;

	public User registerNewUser(RegisterUser request) {
		User user = userRepository.findByEmail(request.getEmail());
		if (user != null) {
			throw new ResourceAccessException("Email này đã được sử dụng");
		}
		return userRepository.save(MapperUtils.createNewUser(request));
	}

	public String login(LoginRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			return jwtProvider.createToken(request.getEmail());
		} catch (AuthenticationException e) {
			throw new ResourceNotFoundException("Email hoặc mật khẩu không đúng!");
		}
	}
}
