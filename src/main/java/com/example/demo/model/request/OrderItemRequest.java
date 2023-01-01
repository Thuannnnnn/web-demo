package com.example.demo.model.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class OrderItemRequest {
    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String productId;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;
    @Min(value = 1, message = "Số lượng sản phẩm không được nhỏ hơn 1")
    private long quantity;
    private double price;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
