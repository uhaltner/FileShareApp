package com.group2.FileShare.ProfileManagement.PasswordRules.MockRules;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRule;

/**
 * Reference: https://stackoverflow.com/questions/16127923/checking-letter-case-upper-lower-within-a-string-in-java
 */

public class LowercaseCharacterRuleMock implements IPasswordRule {

    public LowercaseCharacterRuleMock(){

    }


    @Override
    public boolean isValid(String password) {

        if(password.equals(password.toUpperCase()) == false){
            return true;
        }else{
            return false;
        }
    }
}
