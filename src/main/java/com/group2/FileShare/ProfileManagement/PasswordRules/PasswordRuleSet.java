package com.group2.FileShare.ProfileManagement.PasswordRules;

public class PasswordRuleSet extends PasswordRuleSetAbstract{

    public PasswordRuleSet(){
        super();
        passwordRules.add(new LengthRule());
        passwordRules.add(new LowercaseCharacterRule());
        passwordRules.add(new UppercaseCharacterRule());
        passwordRules.add(new NumericCharacterRule());
    }

}


