package com.group2.FileShare.SignIn;

import com.group2.FileShare.ProfileManagement.IPasswordEncoder;
import com.group2.FileShare.User.User;

public interface ISignInDAO {
	public User getUserWith(SignInForm signInForm, IPasswordEncoder passwordEncoder);
}
