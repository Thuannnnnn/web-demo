package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "price")
	private Float price;
	@Column(name = "price_old")
	private Float priceOld;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private String status;
	@Column(name = "insurance")
	private String insurance;
	@Column(name = "brand_id")
	private Long brandId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "image")
	private String image;
	
	public Employee() {
		
	}
	public Employee( String productName, Float price, Float priceOld, String description, String status,
			String insurance,Long brandId,Long productId,String image) {
//		super();
		this.productName = productName;
		this.price = price;
		this.priceOld = priceOld;
		this.description = description;
		this.status = status;
		this.insurance = insurance;
		this.brandId=brandId;
		this.productId=productId;
		this.image=image;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getPriceOld() {
		return priceOld;
	}
	public void setPriceOld(Float priceOld) {
		this.priceOld = priceOld;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public Long getBrandId() {
	return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getProductId() {
	return productId;
	}
	public void setProductId(Long productId) {
	this.productId = productId;
	}
	public String getImage() {
	return image;
	}
	public void setImage(String image) {
	this.image = image;
	}
	

	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", productName=" + productName + ", price=" + price + ", priceOld=" + priceOld
				+ ", description=" + description + ", status=" + status + ", insurance=" + insurance + ", brandId="
				+ brandId + ", productId=" + productId + ", image=" + image + "]";
	}

	

}
