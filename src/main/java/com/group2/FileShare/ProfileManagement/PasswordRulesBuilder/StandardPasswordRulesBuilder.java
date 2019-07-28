package com.group2.FileShare.ProfileManagement.PasswordRulesBuilder;

import com.group2.FileShare.ProfileManagement.PasswordRules.*;

public class StandardPasswordRulesBuilder implements IPasswordRuleBuilder{

	private PasswordRuleSet passwordRuleSet;

	public StandardPasswordRulesBuilder()
	{
		passwordRuleSet = new PasswordRuleSet();
	}

	public void setRules(String parameter, int value)
	{

		if(parameter == null || parameter.isEmpty()){
			return;
		}

		if(parameter.equals("LENGTH") && value != 0){
			passwordRuleSet.addRule( new LengthRule() );

		}else if(parameter.equals("UPPERCASE") && value != 0) {
			passwordRuleSet.addRule(new UppercaseCharacterRule());

		}else if(parameter.equals("LOWERCASE") && value != 0) {
			passwordRuleSet.addRule(new LowercaseCharacterRule());

		}else if(parameter.equals("NUMERIC") && value != 0) {
			passwordRuleSet.addRule(new NumericCharacterRule());
		}

		return;
	}

	@Override
	public PasswordRuleSet getRules() {
		return passwordRuleSet;
	}
}
