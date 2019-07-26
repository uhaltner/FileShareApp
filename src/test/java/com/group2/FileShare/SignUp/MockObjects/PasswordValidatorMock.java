package com.group2.FileShare.SignUp.MockObjects;

import com.group2.FileShare.ProfileManagement.IPasswordValidator;

public class PasswordValidatorMock implements IPasswordValidator {

	public PasswordValidatorMock(){

	}

	@Override
	public boolean validatePassword(String password, String passwordConfirm) {

		if(password.equals("validPassword1")){
			return true;
		}else{
			return false;
		}
	}
}
