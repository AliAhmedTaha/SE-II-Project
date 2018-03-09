package com.example.form.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Store_Product {
	@Id 
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Integer ID;
	@ManyToOne
    @JoinColumn(name = "ProductID")
private Product product ;
	@ManyToOne
    @JoinColumn(name = "StoreID")
private Store store ;
	
private double price ;

private int buyed;

private int visited ;

public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getID() {
	return ID;
}
public void setID(int iD) {
	ID = iD;
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



}
