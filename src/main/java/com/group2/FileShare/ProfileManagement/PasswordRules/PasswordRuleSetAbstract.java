package com.group2.FileShare.ProfileManagement.PasswordRules;

import java.util.ArrayList;

public abstract class PasswordRuleSetAbstract implements IPasswordRuleSet {

    protected ArrayList<IPasswordRule> passwordRules;

    public PasswordRuleSetAbstract(){
        this.passwordRules = new ArrayList<>();
    }

    public ArrayList<IPasswordRule> getRules(){
        return this.passwordRules;
    }

}
