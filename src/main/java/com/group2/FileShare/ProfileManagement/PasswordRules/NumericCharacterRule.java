package com.group2.FileShare.ProfileManagement.PasswordRules;

/**
 * https://stackoverflow.com/questions/4388546/how-to-determine-whether-a-string-contains-an-integer
 */

public class NumericCharacterRule implements IPasswordRule{

    public NumericCharacterRule(){

    }

    @Override
    public boolean isValid(String password) {

        for(char c : password.toCharArray()){
            if(Character.isDigit(c)){
                return true;
            }
        }

        return false;

    }
}
