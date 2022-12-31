package com.example.demo.model.enums;

public enum Gender {
	MALE("Nam"), FEMALE("Nữ"), ANOTHER("Khác");

	public final String label;

	private Gender(String label) {
		this.label = label;
	}

}
