package com.group2.FileShare.ProfileManagement.PasswordRulesBuilder;

import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;

public interface IPasswordRuleBuilder {

	public void setRules(String parameter, int value);

	public PasswordRuleSet getRules();
}
