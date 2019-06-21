package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.SignUp.SignUpController;
import com.group2.FileShare.SignUp.SignUpForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUpControllerTest {


    @Test
    public void validNewUserTest(){

        SignUpForm form = new SignUpForm();
        form.setFirstName("John");
        form.setLastName("Smith");
        form.setEmail("jsmith@email.com");
        form.setPassword("Password123");
        form.setConfirmPassword("Password123");

        String expectedOutput = "dashboard";

        SignUpController signUpController = new SignUpController();

        assertEquals("validNewUserSignUpTest", expectedOutput , signUpController.signUpUser(form));
    }
}
