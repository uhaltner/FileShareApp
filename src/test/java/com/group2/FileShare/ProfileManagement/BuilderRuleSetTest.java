package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.MockObjects.PasswordRuleDAOMock;
import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.BuilderRuleSet;
import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.StandardPasswordRulesBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuilderRuleSetTest {

	@Test
	public void CreateRuleSetTest(){

		StandardPasswordRulesBuilder builder = new StandardPasswordRulesBuilder();
		BuilderRuleSet ruleSet = new BuilderRuleSet(builder);
		IPasswordRuleDAO dao = new PasswordRuleDAOMock();

		assertEquals("Creating a successful RuleSet", 4, ruleSet.createPasswordRuleSet(dao).getRules().size());
	}

	@Test
	public void NoRuleSetSourceTest(){

		StandardPasswordRulesBuilder builder = new StandardPasswordRulesBuilder();
		IPasswordRuleDAO dao = null;

		BuilderRuleSet ruleSet = new BuilderRuleSet(builder);

		assertEquals("No ruleset source", 0, ruleSet.createPasswordRuleSet(dao).getRules().size());
	}

}
