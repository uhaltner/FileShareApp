package com.group2.FileShare.ProfileManagement.PasswordRules;

import java.util.ArrayList;

public class PasswordRuleSet {

    private ArrayList<IPasswordRule> passwordRules;

    public PasswordRuleSet(){

        passwordRules = new ArrayList<>();

        // Add the password rules
        passwordRules.add(new LengthRuleMock(8,30));
        passwordRules.add(new LowercaseCharacterRuleMock());
        passwordRules.add(new UppercaseCharacterRuleMock());
        passwordRules.add(new NumericCharacterRuleMock());
    }


    public ArrayList<IPasswordRule> getRules(){
        return passwordRules;
    }

}


