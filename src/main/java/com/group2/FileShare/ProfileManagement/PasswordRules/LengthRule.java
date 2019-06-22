package com.group2.FileShare.ProfileManagement.PasswordRules;

public class LengthRule implements IPasswordRule {

    public LengthRule(){

    }


    @Override
    public boolean isValid(String password) {

        String query = "{ call password_length_rule(?) }";

        return PasswordRuleModel.checkRule(query, password);
    }
}
