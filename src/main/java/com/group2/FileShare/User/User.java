package com.group2.FileShare.User;

public class User implements IUser {

	int id;
	String firstName;
	String lastName;
	String email;
	
	
	public User(int userId) {
		this.id = userId;

		UserModelMock dbModel = new UserModelMock();

		firstName = dbModel.getFirstName(userId);
		lastName = dbModel.getLastName(userId);
		email = dbModel.getEmail(userId);
	}

	public User(String firstName, String lastName){
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
