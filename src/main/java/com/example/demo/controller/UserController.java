package com.example.demo.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo.model.ProcessResult;
import com.example.demo.model.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterUser;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user/v1")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value ="taolao",method = RequestMethod.POST)
	public ProcessResult loginUser(@RequestBody @Valid LoginRequest request) {
		
		String sesionId = userService.login(request);
	
		return new ProcessResult(HttpStatus.OK.toString(), "Đăng nhập tài khoản thành công", sesionId);
	}

	@PostMapping(value = "register_new_user")
	public ProcessResult registerNewUser(@RequestBody @Valid RegisterUser request) {
		User user = userService.registerNewUser(request);
		return new ProcessResult(HttpStatus.OK.toString(), "Thêm tài khoản mới thành công", user);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ ResourceAccessException.class })
	public ProcessResult handleInternalException(ResourceAccessException e) {

		return new ProcessResult(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Đã xảy ra lỗi: " + e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ProcessResult handleInputDataException(MethodArgumentNotValidException e) {

		return processFieldErrors(e);
	}

	private ProcessResult processFieldErrors(MethodArgumentNotValidException exception) {
		HashMap<String, String> errorDetail = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String messageError = error.getDefaultMessage();
			errorDetail.put(fieldName, messageError);
		});
		return new ProcessResult(HttpStatus.BAD_REQUEST.toString(), "Dữ liệu nhập vào không hợp lệ!", errorDetail);
	}
}
