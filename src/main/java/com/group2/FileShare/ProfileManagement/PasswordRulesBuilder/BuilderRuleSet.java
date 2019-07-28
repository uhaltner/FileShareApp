package com.group2.FileShare.ProfileManagement.PasswordRulesBuilder;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuilderRuleSet {

	private static final Logger logger = LogManager.getLogger(BuilderRuleSet.class);
	private final IPasswordRuleBuilder builder;

	public BuilderRuleSet(IPasswordRuleBuilder builder){
		this.builder = builder;
	}

	public PasswordRuleSet createPasswordRuleSet(IPasswordRuleDAO passwordRuleDAO){

		try{

			PasswordRulesObject passwordRules = passwordRuleDAO.getPasswordRules();

			for(int i = 0; i < passwordRules.size(); i++){
				builder.setRules(passwordRules.getParameter(i), passwordRules.getValue(i));
			}

		}catch (Exception e){
			logger.log(Level.ERROR, "Error in password rule builder, reverted to default rules" , e);
		}

		return builder.getRules();
	}

}
