package com.excilys.cdb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="appUuser")
@Table(name="appUser")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String role;
	private boolean enabled;
	
	public User() {
		
	}
	
	public User(int id,String username,String password,String role,boolean bool) {
		this.id=id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = bool;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean getDelete() {
		return enabled;
	}
	public void setDelete(boolean enable) {
		this.enabled = enable;
	}
	
	
	
}
