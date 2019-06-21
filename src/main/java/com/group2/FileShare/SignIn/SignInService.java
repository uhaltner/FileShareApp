package com.group2.FileShare.SignIn;

import com.group2.FileShare.User.User;

public class SignInService implements ISignInService {

	@Override
	public User authenticateUserWith(SignInForm signInForm) {
       SignInDAO signInDao = new SignInDAO();
       User user = signInDao.getUserWith(signInForm.getEmail(), signInForm.getPassword());
       return user;
	}

}
