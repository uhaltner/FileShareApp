package com.group2.FileShare.ProfileManagement.PasswordRules;

public class NumericCharacterRule extends PasswordRuleAbstract {

    public NumericCharacterRule(){
        super();
    }

    @Override
    public boolean isValid(String password) {

        String query = "{ call password_numeric_character_rule(?) }";
        return passwordRuleDAO.checkRule(query, password);
    }
}
