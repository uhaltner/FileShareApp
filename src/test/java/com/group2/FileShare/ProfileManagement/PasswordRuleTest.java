package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordRuleTest {

	IPasswordRule lengthRule = new LengthRule();
	IPasswordRule upperCaseRule = new UppercaseCharacterRule();
	IPasswordRule lowerCaseRule = new LowercaseCharacterRule();
	IPasswordRule numericRule = new NumericCharacterRule();

	@Test
	public void LengthRulePass(){
		assertEquals("Password Length Rule Pass", true, lengthRule.isValid("12345678"));
	}

	@Test
	public void LengthRuleFail(){
		assertEquals("Password Length Rule Fail", false, lengthRule.isValid("1234567"));
	}

	@Test
	public void UpperCaseRulePass(){
		assertEquals("Password Upper Case Rule Pass", true, upperCaseRule.isValid("Uppercase"));
	}

	@Test
	public void UpperCaseRuleFail(){
		assertEquals("Password Upper Case Rule Fail", false, upperCaseRule.isValid("uppercase"));
	}

	@Test
	public void LowerCaseRulePass(){
		assertEquals("Password Lower Case Rule Pass", true, lowerCaseRule.isValid("lowercase"));
	}

	@Test
	public void LowerCaseRuleFail(){
		assertEquals("Password Lower Case Rule Fail", false, lowerCaseRule.isValid("LOWERCASE"));
	}

	@Test
	public void NumericRulePass(){
		assertEquals("Password Numeric Rule Pass", true, numericRule.isValid("1Number"));
	}

	@Test
	public void NumericRuleFail(){
		assertEquals("Password Numeric Rule Fail", false, numericRule.isValid("noNumber"));
	}





}
