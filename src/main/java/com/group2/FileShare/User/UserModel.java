package com.group2.FileShare.User;

public class UserModel implements IUserModel {

    /** Pull user attributes form the database **/

    @Override
    public String pullFirstName(int userId){

        //DB Implementation
        return "";
    }

    @Override
    public String pullLastName(int userId){

        //DB Implementation
        return "";
    }

    @Override
    public String pullEmail(int userId){

        //DB Implementation
        return "";
    }

    @Override
    public String pullPassword(int userId){

        //DB Implementation
        return "";
    }

    @Override
    public boolean exist(int userId){

        //DB Implementation
        return false;
    }

    @Override
    public int pullUserId(String email, String password){

        //DB Implementation
        return 1;
    }

    /** Push user attributes to the database **/

    @Override
    public void pushFirstName(int userId, String firstName){

        //DB Implementation
        return;
    }

    @Override
    public void pushLastName(int userId, String lastName){

        //DB Implementation
        return;
    }

    @Override
    public void pushEmail(int userId, String email){

        //DB Implementation
        return;
    }

    @Override
    public void pushPassword(int userId, String hashedPassword){

        //DB Implementation
        return;
    }
}
