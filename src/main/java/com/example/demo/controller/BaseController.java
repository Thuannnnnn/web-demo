package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.response.ProcessResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class BaseController {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ProcessResult handleInternalException(ResourceNotFoundException e) {

        return new ProcessResult(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Đã xảy ra lỗi: " + e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ProcessResult handleInputDataException(MethodArgumentNotValidException e) {

        return processFieldErrors(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class})
    protected ProcessResult handleNoElementException(NoSuchElementException e) {

        return new ProcessResult(HttpStatus.NOT_FOUND.toString(), "Đã xảy ra lỗi: " + e.getMessage(), e.getCause());
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
