package com.example.form.Entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Brand {
	@Id
	private String name;
	private String Category;
	@OneToMany(mappedBy="Brand",cascade= CascadeType.ALL)
	private Set<Product>Produts;
	
	public Brand() {
		super();
	}
	public Brand(String name, String category, double price, int quantity) {
		super();
		this.name = name;
		Category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
}
