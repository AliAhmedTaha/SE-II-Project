package com.example.form.Entities;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private String name;
	private String Category;
	private String Brand;
	private double HighPrice;
	private double LowPrice;
	private String Type;
	@ManyToOne 
	@JoinColumn(name= "Brand_name")
	private Brand brand;
	
	
	@OneToMany (mappedBy ="product" )
	Set <Store_Product> products;	
	
	
	public Product()
	{
		
	}
	
	public Product(String name, String brand, String type, int price, int lowprice,
			int highprice) {
		super();
		this.name = name;
		Brand = brand;
		Type = type;
		this.LowPrice = lowprice;
		this.HighPrice = highprice;
	}

	
	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public double getHighPrice() {
		return HighPrice;
	}

	public void setHighPrice(double highPrice) {
		HighPrice = highPrice;
	}

	public double getLowPrice() {
		return LowPrice;
	}

	public void setLowPrice(double lowPrice) {
		LowPrice = lowPrice;
	}


}