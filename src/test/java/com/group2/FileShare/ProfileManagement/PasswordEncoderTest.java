package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncoderTest {

    @Test
    public void passwordsMatchTest(){

        PasswordEncoder pe = new PasswordEncoder();

        String rawPassword = "Password123";
        String hashedPassword = pe.hashPassword(rawPassword);

        assertEquals("HashPassword Match Test", true, pe.matches(rawPassword, hashedPassword));
    }

    @Test
    public void passwordsNotMatchTest(){

        PasswordEncoder pe = new PasswordEncoder();

        String rawPassword = "Password123";
        String hashedPassword = pe.hashPassword("Password456");

        assertEquals("HashPassword Not Match Test", false, pe.matches(rawPassword, hashedPassword));
    }
}
