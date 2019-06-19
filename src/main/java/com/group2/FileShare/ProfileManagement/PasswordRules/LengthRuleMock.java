package com.group2.FileShare.ProfileManagement.PasswordRules;

public class LengthRuleMock implements IPasswordRule{

    private int minLength;
    private int maxLength;

    public LengthRuleMock(int min, int max){

        this.minLength = min;
        this.maxLength = max;
    }

    @Override
    public boolean isValid(String password) {

        int passwordLength = password.length();

        if (passwordLength >= minLength && passwordLength <= maxLength) {
            return true;
        } else{
            return false;
        }
    }
}
