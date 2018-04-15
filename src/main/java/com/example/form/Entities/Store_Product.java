package com.example.form.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Store_Product {

	@Id
	private String ID;

	@ManyToOne
	@JoinColumn(name = "ProductID")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "StoreID")
	private Store store;

	private double price;
	private String BrandId;
	private int buyed;

	private int visited;
	private int Quantity;

	public Store_Product(Product product, Store store, double price, String BrandId, int buyed, int visited, int quantity) {
		super();
		this.product = product;
		this.store = store;
		this.price = price;
		this.BrandId=BrandId;
		this.buyed = buyed;
		this.visited = visited;
		Quantity = quantity;
		ID = store.getStorename()+ "@" +product.getName();
	}

	public Store_Product(String iD, double price, String BrandId, int buyed, int visited, int quantity) {
		super();
		ID = iD;
		this.price = price;
		this.BrandId=BrandId;
		this.buyed = buyed;
		this.visited = visited;
		Quantity = quantity;
	}

	public Store_Product() {
		super();
	}

	public String getProductName() {
		return product.getName();
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public int getBuyed() {
		return buyed;
	}

	public void setBuyed(int buyed) {
		this.buyed = buyed;
	}

	public int getVisited() {
		return visited;
	}

	public void setVisited(int visited) {
		this.visited = visited;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public Store getStore() {
		return store;
	}

}





