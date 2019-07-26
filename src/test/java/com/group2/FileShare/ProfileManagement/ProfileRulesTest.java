package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.PasswordRulesObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileRulesTest {

	@Test
	public void emptyPasswordRulesTest(){
		PasswordRuleDAO passwordRuleDAO = new PasswordRuleDAO();
		PasswordRulesObject passwordRules = new PasswordRulesObject();

		passwordRules = passwordRuleDAO.getPasswordRules();

		assertEquals("Sorted by name document list test", false, passwordRules.isEmpty());

	}
}
