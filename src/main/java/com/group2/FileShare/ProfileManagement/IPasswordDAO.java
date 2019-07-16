package com.group2.FileShare.ProfileManagement;

public interface IPasswordDAO {

    public void updatePassword(int userId, String rawNewPassword);
    public void updateRecoveryPassword(String userEmail, String rawNewPassword);
}
