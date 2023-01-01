package com.example.demo.model.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class NewOrderRequest {
    private String createdBy;
    @NotBlank(message = "Nhập địa chỉ nhận hàng")
    private String address;
    private String description;
    @NotBlank(message = "Nhập số điện thoại nhận hàng")
    private String phoneNumber;
    private String title;
    @NotBlank(message = "Nhập Họ và tên nhận hàng")
    private String fullName;
    @NotBlank(message = "Nhập email để xác nhận đơn hàng")
    @Email(message = "Đây không phải là địa chỉ email")
    private String email;
    @NotEmpty(message = "Xin hãy thêm danh sách sản phẩm để đặt hàng")
    @Valid
    private List<OrderItemRequest> items;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
