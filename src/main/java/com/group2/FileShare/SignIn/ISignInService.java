package com.group2.FileShare.SignIn;

import com.group2.FileShare.User.User;

public interface ISignInService {
	public User authenticateUserWith(SignInForm signInForm);
}
