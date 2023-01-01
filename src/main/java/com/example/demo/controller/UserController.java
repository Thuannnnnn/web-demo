package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.response.ProcessResult;
import com.example.demo.model.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterUser;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user/v1")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping(value ="login",method = RequestMethod.POST)
	public ProcessResult loginUser(@RequestBody @Valid LoginRequest request) {
		
		String sessionId = userService.login(request);
	
		return new ProcessResult(HttpStatus.OK.toString(), "Đăng nhập tài khoản thành công", sessionId);
	}

	@PostMapping(value = "register_new_user")
	public ProcessResult registerNewUser(@RequestBody @Valid RegisterUser request) {
		User user = userService.registerNewUser(request);
		return new ProcessResult(HttpStatus.OK.toString(), "Thêm tài khoản mới thành công", user);
	}
}
