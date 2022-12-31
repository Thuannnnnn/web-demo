package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.model.enums.Gender;
import com.example.demo.model.request.RegisterUser;

public class MapperUtils {

	public static User createNewUser(RegisterUser request) {

		User newUser = new User();
		newUser.setAge(request.getAge());
		newUser.setEmail(request.getEmail());
		newUser.setUsername(request.getUsername());
		newUser.setLastName(request.getLastName());
		newUser.setFirstName(request.getFirstName());
//		newUser.setGender(Gender.valueOf(request.getGender()));

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(request.getPassword()));
		return newUser;
	}
}
