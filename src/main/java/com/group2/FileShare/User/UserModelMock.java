package com.group2.FileShare.User;

public class UserModelMock implements IUserModel {

    @Override
    public String pullFirstName(int userId) {

        switch(userId){
            case 1:
                return "John";
            case 2:
                return "James";
             default:
                 return "defaultFirstName";
        }

    }

    @Override
    public String pullLastName(int userId) {

        switch(userId){
            case 1:
                return "Smith";
            case 2:
                return "Cake";
            default:
                return "defaultLastName";
        }
    }

    @Override
    public String pullEmail(int userId) {

        switch(userId){
            case 1:
                return "john.smith@email.com";
            case 2:
                return "james.cake@email.com";
            default:
                return "nobody@email.com";
        }
    }

    @Override
    public String pullPassword(int userId){
        return "Password123";
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
        System.out.println("UserId: " + userId + " -> First Name: " + firstName);
    }

    @Override
    public void pushLastName(int userId, String lastName){
        System.out.println("UserId: " + userId + " -> First Name: " + lastName);
    }

    @Override
    public void pushEmail(int userId, String email){
        System.out.println("UserId: " + userId + " -> First Name: " + email);
    }

    @Override
    public void pushPassword(int userId, String hashedPassword){
        System.out.println("UserId: " + userId + " -> First Name: " + hashedPassword);
    }
}
