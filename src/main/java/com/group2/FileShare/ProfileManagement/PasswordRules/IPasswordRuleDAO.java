package com.group2.FileShare.ProfileManagement.PasswordRules;

public interface IPasswordRuleDAO {

    public boolean checkRule(String query, String password);
}
