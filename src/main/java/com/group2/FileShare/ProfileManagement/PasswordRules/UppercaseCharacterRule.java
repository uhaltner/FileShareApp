package com.group2.FileShare.ProfileManagement.PasswordRules;

public class UppercaseCharacterRule extends PasswordRuleAbstract {

    public UppercaseCharacterRule(){
        super();
    }

    @Override
    public boolean isValid(String password) {

        String query = "{ call password_uppercase_character_rule(?) }";
        return passwordRuleDAO.checkRule(query, password);
    }
}
