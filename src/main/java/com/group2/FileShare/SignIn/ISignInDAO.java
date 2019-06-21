package com.group2.FileShare.SignIn;

import com.group2.FileShare.User.User;

public interface ISignInDAO {
	public User getUserWith(String email, String password);
}
