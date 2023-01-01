package com.example.demo.model.enums;

/**
 *
 */
public enum StatusType {
    CREATED(0, "Mới"),
    APPROVED(1, "Phê Duyệt"),
    COMPLETED(2, "Hoàn thành"),
    CANCELLED(3, "Hủy Bỏ");

    public final int value;
    public final String label;

    StatusType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getStatusDescription(int value) {
        switch (value) {
            case 1:
                return APPROVED.label;
            case 2:
                return COMPLETED.label;
            case 3:
                return CANCELLED.label;
            default:
                return CREATED.label;
        }
    }
}
