package com.example.demo.service;

import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.MapperUtils;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @PostConstruct
    void init() {
        String mailAdmin = "admin@gmail.com";
        if (userRepository.findByEmail(mailAdmin) == null) {
            User userAdmin = new User();
            userAdmin.setRole(UserRole.ADMIN);
            userAdmin.setEmail(mailAdmin);
            userAdmin.setGender(Gender.MALE);
            userAdmin.setFirstName("Thuận");
            userAdmin.setLastName("Võ Minh");
            userAdmin.setUsername("admin");

            PasswordEncoder encoder = new BCryptPasswordEncoder();
            userAdmin.setPassword(encoder.encode("123456"));

            userRepository.save(userAdmin);
        }
    }

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
