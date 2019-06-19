package com.group2.FileShare.User;

public class UserModelMock implements IUserModel {
    @Override
    public String getFirstName(int userId) {

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
    public String getLastName(int userId) {

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
    public String getEmail(int userId) {

        switch(userId){
            case 1:
                return "john.smith@email.com";
            case 2:
                return "james.cake@email.com";
            default:
                return "nobody@email.com";
        }
    }
}
