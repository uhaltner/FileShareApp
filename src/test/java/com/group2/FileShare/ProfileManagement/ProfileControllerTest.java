package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordForm;
import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRule;
import com.group2.FileShare.ProfileManagement.ProfileController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileControllerTest {

    @Test
    public void updateProfileValidMatchingPasswords(){
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setPassword("Password123");
        passwordForm.setConfirmPassword("Password123");

        String expectedOutput = "dashboard";

        ProfileController profile = new ProfileController();

        assertEquals("updateProfileValidMatchingPasswords", expectedOutput , profile.updateProfile(passwordForm));
    }




}
