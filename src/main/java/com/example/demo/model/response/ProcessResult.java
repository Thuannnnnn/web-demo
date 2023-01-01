package com.example.demo.model.response;

public class ProcessResult {
	private final String status;
	private final String message;
	private final Object data;

	public ProcessResult(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

}
