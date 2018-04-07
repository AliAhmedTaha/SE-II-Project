package com.example.form.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Store {
	@Id
	private String Storename;
	private String Address;
	private boolean State;
	private String Type;
	private int numvisitors;
	private int numbuyers ;

	public int getNumvisitors() {
		return numvisitors;
	}

	public void setNumvisitors(int numvisitors) {
		this.numvisitors = numvisitors;
	}

	public int getNumbuyers() {
		return numbuyers;
	}

	public void setNumbuyers(int numbuyers) {
		this.numbuyers = numbuyers;
	}

	@ManyToOne
	@JoinColumn(name = "user_name")
	private User user;

	@OneToMany(mappedBy = "store")
	Set<Store_Product> stores;

	

	public Store() 
	{
		super();
	}

	public Store(String storename, String address, boolean state, String type) 
	{
		super();
		Storename = storename;
		Address = address;
		State = state;
		Type = type;
		this.numbuyers=0 ;
		this.numvisitors=0;
	}
	

	public Store(String storename, String address, boolean state, String type, int numvisitors, int numbuyers) {
		super();
		Storename = storename;
		Address = address;
		State = state;
		Type = type;
		this.numvisitors = numvisitors;
		this.numbuyers = numbuyers;
	}

	public User getUser() 
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String getStorename()
	{
		return Storename;
	}

	public void setStorename(String storename) {
		Storename = storename;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public boolean getState() {
		return State;
	}

	public void setState(boolean state) {
		State = state;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
