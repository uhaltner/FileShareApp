package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRulesBuilder.StandardPasswordRulesBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StandardPasswordRuleBuilderTest {

	private StandardPasswordRulesBuilder builder = new StandardPasswordRulesBuilder();

	@Test
	public void RuleTurnedOnTest(){
		builder.setRules("LENGTH",1);

		assertEquals("Rule is ON, on list", false, builder.getRules().getRules().isEmpty());
	}

	@Test
	public void RuleTurnedOffTest(){
		builder.setRules("LENGTH",0);

		assertEquals("Rule is OFF, not on list", true, builder.getRules().getRules().isEmpty());
	}

	@Test
	public void RuleStatusOtherTest(){
		builder.setRules("LENGTH",10);

		assertEquals("Rule is ON, on list", false, builder.getRules().getRules().isEmpty());
	}

	@Test
	public void RuleIsNullTest(){
		builder.setRules(null,0);

		assertEquals("Rule is Null", true, builder.getRules().getRules().isEmpty());
	}

	@Test
	public void NonExisitngRuleTestTest(){
		builder.setRules("SPECIAL",10);

		assertEquals("Rule does not exist", true, builder.getRules().getRules().isEmpty());
	}

}
