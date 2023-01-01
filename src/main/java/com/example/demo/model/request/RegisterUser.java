package com.example.demo.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.demo.model.enums.Gender;

public class RegisterUser {
	@NotBlank(message = "Xin vui lòng nhập tên người dùng")
	private String username;
	@NotBlank(message = "Xin vui lòng nhập email")
	@Email(message = "Đây không phải là email")
	private String email;
	@NotBlank(message = "Xin vui lòng nhập tên")
	private String firstName;
	@NotBlank(message = "Xin vui lòng nhập họ")
	private String lastName;
//	private String gender;
	private int age;
	@NotBlank(message = "Xin vui lòng nhập khẩu")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
