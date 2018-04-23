package com.example.form.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Store_Contributors {

	@Id
	private String ID;

	@ManyToOne
	@JoinColumn(name = "ContributorName")
	private User user;

	@ManyToOne
	@JoinColumn(name = "StoreName")
	private Store store;
	private String Permission;
	
	
	
	public Store_Contributors(String iD, User user, Store store, String permission) {
		super();
		ID = iD;
		this.user = user;
		this.store = store;
		Permission = permission;
	}
	
	public Store_Contributors(User user, Store store, String permission) {
		super();
		this.user = user;
		this.store = store;
		Permission = permission;
		ID = store.getStorename()+ "@" +user.getName();
	}
	
	public Store_Contributors() {
		super();
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getPermission() {
		return Permission;
	}
	public void setPermission(String permission) {
		Permission = permission;
	}	
}
