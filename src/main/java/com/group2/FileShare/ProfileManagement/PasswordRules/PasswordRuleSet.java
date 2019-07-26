package com.group2.FileShare.ProfileManagement.PasswordRules;

import java.util.ArrayList;

public class PasswordRuleSet implements IPasswordRuleSet{

    private ArrayList<IPasswordRule> passwordRules;

    public PasswordRuleSet(){
        this.passwordRules = new ArrayList<>();
    }

    @Override
    public ArrayList<IPasswordRule> getRules(){
        return this.passwordRules;
    }

    @Override
    public void addRule(IPasswordRule rule){
        this.passwordRules.add(rule);
    }
}


