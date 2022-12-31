package com.example.demo.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.model.ProcessResult;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class HandlerExceptionResponse {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ProcessResult handleInternalException(ResourceNotFoundException e) {

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
