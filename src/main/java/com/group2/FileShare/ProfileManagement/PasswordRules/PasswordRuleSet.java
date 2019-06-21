package com.group2.FileShare.ProfileManagement.PasswordRules;

import java.util.ArrayList;

public class PasswordRuleSet {

    public static ArrayList<IPasswordRule> getRules(){

        ArrayList<IPasswordRule> passwordRules = new ArrayList<>();

        // Add the password rules
        passwordRules.add(new LengthRule(8,30));
        passwordRules.add(new LowercaseCharacterRule());
        passwordRules.add(new UppercaseCharacterRule());
        passwordRules.add(new NumericCharacterRule());

        return passwordRules;
    }

}


