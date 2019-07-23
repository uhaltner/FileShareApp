package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import com.group2.FileShare.ProfileManagement.PasswordValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordValidatorTest {

    IPasswordRuleSet passwordRuleSet = new PasswordRuleSet();

    @Test
    public void emptyPasswordTest(){
        PasswordValidator v = new PasswordValidator();
        assertEquals("emptyPasswordTest", false, v.validatePassword("","password",new ArrayList<IPasswordRule>()));
    }

    @Test
    public void nullPasswordTest(){
        PasswordValidator v = new PasswordValidator();
        assertEquals("nullPasswordTest", false, v.validatePassword(null,"password",new ArrayList<IPasswordRule>()));
    }

    @Test
    public void passwordMatchTest(){
        PasswordValidator v = new PasswordValidator();
        assertEquals("passwordMatchTest", true, v.validatePassword("Test123","Test123",new ArrayList<IPasswordRule>()));
    }

    @Test
    public void validPasswordRulesTest(){

        PasswordValidator v = new PasswordValidator();
        assertEquals("validPasswordRulesTest", true, v.validatePassword("Password123","Password123", passwordRuleSet.getRules()));
    }

    @Test
    public void shortPasswordTest(){

        PasswordValidator v = new PasswordValidator();
        assertEquals("shortPasswordTest", false, v.validatePassword("Pass1","Pass1", passwordRuleSet.getRules()));
    }

    @Test
    public void noUpperCasePasswordTest(){

        PasswordValidator v = new PasswordValidator();
        assertEquals("noUpperCasePasswordTest", false, v.validatePassword("password1","password1", passwordRuleSet.getRules()));
    }

    @Test
    public void noLowerCasePasswordTest(){

        PasswordValidator v = new PasswordValidator();
        assertEquals("noLowerCasePasswordTest", false, v.validatePassword("PASSWORD1","PASSWORD1", passwordRuleSet.getRules()));
    }

    @Test
    public void noNumberPasswordTest(){

        PasswordValidator v = new PasswordValidator();
        assertEquals("noNumberPasswordTest", false, v.validatePassword("SUPERPASSWORD","SUPERPASSWORD", passwordRuleSet.getRules()));
    }



}
