package com.group2.FileShare.User;

public interface IUserModel {


    /** Pull user attributes form the database **/

    public String pullFirstName(int userId);

    public String pullLastName(int userId);

    public String pullEmail(int userId);

    public String pullPassword(int userId);

    public boolean userExist(int userId);

    public int pullUserId(String email, String password);

    /** Push user attributes to the database **/

    public void pushFirstName(int userId, String firstName);

    public void pushLastName(int userId, String lastName);

    public void pushEmail(int userId, String email);

    public void pushPassword(int userId, String password);
}
