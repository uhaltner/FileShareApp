package com.group2.FileShare.ProfileManagement;

public interface IPasswordEncoder {

    public String hashPassword(String password);

    public boolean matches(String password, String encryptedPassword);
}
