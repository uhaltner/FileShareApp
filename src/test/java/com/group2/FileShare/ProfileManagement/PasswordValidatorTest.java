package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.MockObjects.PasswordRuleDAOMock;
import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordValidatorTest {

    IPasswordRuleDAO passwordRuleDAO = new PasswordRuleDAOMock();

    @Test
    public void emptyPasswordTest(){
        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("emptyPasswordTest", false, v.validatePassword("","password"));
    }

    @Test
    public void nullPasswordTest(){
        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("nullPasswordTest", false, v.validatePassword(null,"password"));
    }

    @Test
    public void passwordNotMatchTest(){
        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("Non Matching Password Test", false, v.validatePassword("Test1235","Test1234"));
    }

    @Test
    public void validPasswordRulesTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("validPasswordRulesTest", true, v.validatePassword("Password123","Password123"));
    }

    @Test
    public void notValidPasswordRulesTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("NotValidPasswordRulesTest", false, v.validatePassword("Password","Password"));
    }

    @Test
    public void shortPasswordTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("shortPasswordTest", false, v.validatePassword("Pass1","Pass1"));
    }

    @Test
    public void noUpperCasePasswordTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("noUpperCasePasswordTest", false, v.validatePassword("password1","password1"));
    }

    @Test
    public void noLowerCasePasswordTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("noLowerCasePasswordTest", false, v.validatePassword("PASSWORD1","PASSWORD1"));
    }

    @Test
    public void noNumberPasswordTest(){

        PasswordValidator v = new PasswordValidator(passwordRuleDAO);
        assertEquals("noNumberPasswordTest", false, v.validatePassword("SUPERPASSWORD","SUPERPASSWORD"));
    }

}
