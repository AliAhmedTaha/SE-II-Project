package com.example.form.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class LogProduct {

	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;
	 private String type; 
	private String ProductID;
	private double price;
	private String BrandId;
	private int Quantity;
	
	public LogProduct() {
		super();
	}
	public LogProduct(Integer id, String productID, double price, String brandId, int quantity) {
		super();
		this.id = id;
		ProductID = productID;
		this.price = price;
		BrandId = brandId;
		Quantity = quantity;
	}
	
	public LogProduct(String type, String productID, double price, String brandId, int quantity) {
		super();
		this.type = type;
		ProductID = productID;
		this.price = price;
		BrandId = brandId;
		Quantity = quantity;
	}
	public LogProduct(String Type, Store_Product product) {
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getBrandId() {
		return BrandId;
	}
	public void setBrandId(String brandId) {
		BrandId = brandId;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public Store_Product toStoreProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
