package com.example.form.Entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private String name ;
	private String Password ;
	private String Type ;
	@OneToMany(mappedBy="user",cascade= CascadeType.ALL)
	private Set<Store>stores;
	
	public User() {
		super();
	}
	public User(String name, String password, String type) {
		super();
		this.name = name;
		Password = password;
		Type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
    

}
