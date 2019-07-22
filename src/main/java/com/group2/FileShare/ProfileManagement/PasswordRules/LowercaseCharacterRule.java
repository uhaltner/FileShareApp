package com.group2.FileShare.ProfileManagement.PasswordRules;

public class LowercaseCharacterRule extends PasswordRuleAbstract {

    public LowercaseCharacterRule(){
        super();
    }

    @Override
    public boolean isValid(String password) {

        String query = "{ call password_lowercase_character_rule(?) }";
        return passwordRuleDAO.checkRule(query, password);
    }
}
