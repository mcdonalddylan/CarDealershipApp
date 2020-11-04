package com.dealership.model;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private boolean rememberMe;
	private boolean isEmployee;
	private boolean isSystem;
	private boolean freshUser = false;

	public User()
	{
		username = "Empty";
		password = "Empty";
		firstName = "Empty";
		lastName = "Empty";
		rememberMe = false;
		isEmployee = false;
		isSystem = false;
		setFreshUser(true);
	}
	
	public User(String user, String pass, String first, String last, 
			boolean remember, boolean isE, boolean isS)
	{
		username = user;
		password = pass;
		firstName = first;
		lastName = last;
		rememberMe = remember;
		isEmployee = isE;
		isSystem = isS;
		
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

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isFreshUser() {
		return freshUser;
	}

	public void setFreshUser(boolean freshUser) {
		this.freshUser = freshUser;
	}
}
