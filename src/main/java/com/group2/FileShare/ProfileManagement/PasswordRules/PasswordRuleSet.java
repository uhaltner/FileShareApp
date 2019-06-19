package com.group2.FileShare.ProfileManagement.PasswordRules;

import java.util.ArrayList;

public class PasswordRuleSet {

    private ArrayList<IPasswordRule> passwordRules;

    public PasswordRuleSet(){

        passwordRules = new ArrayList<>();

        // Add the password rules
        passwordRules.add(new LengthRule(8,30));
        passwordRules.add(new LowercaseCharacterRule());
        passwordRules.add(new UppercaseCharacterRule());
        passwordRules.add(new NumericCharacterRule());
    }


    public ArrayList<IPasswordRule> getRules(){
        return passwordRules;
    }

}


