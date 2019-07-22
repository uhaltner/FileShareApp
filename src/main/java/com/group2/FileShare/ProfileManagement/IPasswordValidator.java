package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRule;

import java.util.ArrayList;

public interface IPasswordValidator {

    public boolean validatePassword(String password, String passwordConfirm, ArrayList<IPasswordRule> passwordRuleList);
    public boolean verifyRules(String password, ArrayList<IPasswordRule> passwordRuleList);

}
