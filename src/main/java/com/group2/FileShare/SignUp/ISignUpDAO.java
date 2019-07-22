package com.group2.FileShare.SignUp;

public interface ISignUpDAO {

    public boolean userExist(String email);
    public int createProfile(String firstName, String lastName, String email, String rawPassword);
}
