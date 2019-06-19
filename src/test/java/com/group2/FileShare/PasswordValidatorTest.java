package com.group2.FileShare;

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
    public void passwordRulesTest(){

        PasswordValidator v = new PasswordValidator();

        //create rules
        ArrayList<IPasswordRule> passwordRules= new ArrayList<>();
        passwordRules.add(new LengthRuleMock(8,30));
        passwordRules.add(new LowercaseCharacterRuleMock());
        passwordRules.add(new UppercaseCharacterRuleMock());
        passwordRules.add(new NumericCharacterRuleMock());

        assertEquals("passwordRulesTest", true, v.validatePassword("Password123","Password123", passwordRules));
    }

}
