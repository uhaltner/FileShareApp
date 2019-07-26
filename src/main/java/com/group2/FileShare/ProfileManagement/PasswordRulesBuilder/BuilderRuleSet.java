package com.group2.FileShare.ProfileManagement.PasswordRulesBuilder;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;

public class BuilderRuleSet {

	private final IPasswordRuleBuilder builder;

	public BuilderRuleSet(IPasswordRuleBuilder builder){
		this.builder = builder;
	}

	public PasswordRuleSet createPasswordRuleSet(IPasswordRuleDAO passwordRuleDAO){

		PasswordRulesObject passwordRules = passwordRuleDAO.getPasswordRules();

		for(int i = 0; i < passwordRules.size(); i++){
			builder.setRules(passwordRules.getParameter(i), passwordRules.getValue(i));
		}

		return builder.getRules();
	}

}
