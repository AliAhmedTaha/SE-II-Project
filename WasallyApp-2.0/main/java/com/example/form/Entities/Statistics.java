package com.example.form.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Statistics {

	@Id
	private String Statname;
	private boolean Status;

	public Statistics() {
		super();
	}

	public Statistics(String statname, boolean status) {
		super();
		Statname = statname;
		Status = status;
	}

	public String getStatname() {
		return Statname;
	}

	public void setStatname(String statname) {
		Statname = statname;
	}

	public boolean getStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

}
