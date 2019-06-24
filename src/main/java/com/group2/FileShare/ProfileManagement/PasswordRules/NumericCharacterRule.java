package com.group2.FileShare.ProfileManagement.PasswordRules;

public class NumericCharacterRule implements IPasswordRule {

    public NumericCharacterRule(){

    }


    @Override
    public boolean isValid(String password) {

        String query = "{ call password_numeric_character_rule(?) }";

        return PasswordRuleModel.checkRule(query, password);
    }
}
