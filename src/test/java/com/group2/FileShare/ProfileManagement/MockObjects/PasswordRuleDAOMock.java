package com.group2.FileShare.ProfileManagement.MockObjects;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.PasswordRulesObject;

public class PasswordRuleDAOMock implements IPasswordRuleDAO {

	@Override
	public boolean checkRule(String query, String password) {


		//this will never be reached as password rules use the real DAO.
		return false;
	}

	@Override
	public PasswordRulesObject getPasswordRules() {

		PasswordRulesObject passwordRules = new PasswordRulesObject();

		passwordRules.addParameter("LENGTH");
		passwordRules.addValue(1);

		passwordRules.addParameter("UPPERCASE");
		passwordRules.addValue(1);

		passwordRules.addParameter("LOWERCASE");
		passwordRules.addValue(1);

		passwordRules.addParameter("NUMERIC");
		passwordRules.addValue(1);

		return passwordRules;
	}
}
