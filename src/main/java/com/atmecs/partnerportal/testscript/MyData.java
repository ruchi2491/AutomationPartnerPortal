package com.atmecs.partnerportal.testscript;

public class MyData {

	private String username;
	private String password;
	private String name;
	private String id;
	
	
	public MyData() {
		
	}
	public MyData(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	} 
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		String str = username + "  "+ password + "   "+ name ;
		return str;
	}
	
}
