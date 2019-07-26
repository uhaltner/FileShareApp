package com.group2.FileShare.ProfileManagement.MockObjects;

import com.group2.FileShare.SignUp.ISignUpDAO;

public class SignUpDAOMock implements ISignUpDAO {

	@Override
	public boolean userExist(String email) {
		if(email.equals("valid@email.com")){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int createProfile(String firstName, String lastName, String email, String rawPassword) {
		return 1;
	}
}
