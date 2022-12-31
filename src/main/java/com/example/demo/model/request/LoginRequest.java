package com.example.demo.model.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank(message = "Xin vui lòng nhập email")
	private String email;
	@NotBlank(message = "Xin vui lòng nhập mật khẩu")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
