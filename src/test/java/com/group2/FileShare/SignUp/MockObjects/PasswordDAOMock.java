package com.group2.FileShare.SignUp.MockObjects;

import com.group2.FileShare.ProfileManagement.IPasswordDAO;

public class PasswordDAOMock implements IPasswordDAO {

	@Override
	public void updatePassword(int userId, String rawNewPassword) {
		return;
	}

	@Override
	public void updateRecoveryPassword(String userEmail, String rawNewPassword) {
		return;
	}
}
