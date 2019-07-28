package com.group2.FileShare.ProfileManagement.MockObjects;

import com.group2.FileShare.ProfileManagement.IPasswordDAO;

public class PasswordDAOMock implements IPasswordDAO {

	public void updatePassword(int userId, String rawNewPassword){
		return;
	}
	public void updateRecoveryPassword(String userEmail, String rawNewPassword){
		return;
	}
}
