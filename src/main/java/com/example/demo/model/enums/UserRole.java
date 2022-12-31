package com.example.demo.model.enums;

public enum UserRole {
	EMPLOYEE("Nhân viên"), CUSTOMER("Khách hàng"), ADMIN("Quản trị viên");

	public final String label;

	private UserRole(String label) {
		this.label = label;
	}

}
