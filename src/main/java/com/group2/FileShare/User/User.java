package com.group2.FileShare.User;

public class User implements IUser {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	
	public User(int userId, String email, String firstName, String lastName) {
		this.id = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	public int getId(){
		return this.id;
	}

}
