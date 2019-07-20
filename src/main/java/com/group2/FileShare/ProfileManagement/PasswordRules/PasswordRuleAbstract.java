package com.group2.FileShare.ProfileManagement.PasswordRules;

public abstract class PasswordRuleAbstract implements IPasswordRule{

    protected IPasswordRuleDAO passwordRuleDAO;

    public PasswordRuleAbstract(){
        this.passwordRuleDAO = new PasswordRuleDAO();
    }

    public abstract boolean isValid(String password);
}
