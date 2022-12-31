package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "SELECT * FROM user_db p WHERE p.email = ?1", nativeQuery = true)
	User findByEmail(String email);

}
