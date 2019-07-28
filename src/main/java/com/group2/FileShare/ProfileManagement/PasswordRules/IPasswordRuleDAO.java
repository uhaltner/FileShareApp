package com.group2.FileShare.ProfileManagement.PasswordRules;

import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.PasswordRulesObject;

public interface IPasswordRuleDAO {

    public boolean checkRule(String query, String password);
    public PasswordRulesObject getPasswordRules();
}
