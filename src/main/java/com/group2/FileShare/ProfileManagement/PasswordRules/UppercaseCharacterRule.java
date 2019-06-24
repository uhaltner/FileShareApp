package com.group2.FileShare.ProfileManagement.PasswordRules;

public class UppercaseCharacterRule implements IPasswordRule {

    public UppercaseCharacterRule(){

    }


    @Override
    public boolean isValid(String password) {

        String query = "{ call password_uppercase_character_rule(?) }";

        return PasswordRuleModel.checkRule(query, password);
    }
}
