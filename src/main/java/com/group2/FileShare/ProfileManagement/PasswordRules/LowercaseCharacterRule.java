package com.group2.FileShare.ProfileManagement.PasswordRules;

public class LowercaseCharacterRule implements IPasswordRule {

    public LowercaseCharacterRule(){

    }


    @Override
    public boolean isValid(String password) {

        String query = "{ call password_lowercase_character_rule(?) }";

        return PasswordRuleModel.checkRule(query, password);
    }
}
