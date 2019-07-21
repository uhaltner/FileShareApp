package com.group2.FileShare.ProfileManagement.PasswordRules;

public class LengthRule extends PasswordRuleAbstract {

    public LengthRule(){
        super();
    }

    @Override
    public boolean isValid(String password) {

        String query = "{ call password_length_rule(?) }";
        return passwordRuleDAO.checkRule(query, password);
    }
}
