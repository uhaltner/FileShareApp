package com.group2.FileShare;

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

        PasswordEncoder e = new PasswordEncoder();

        String textPassword = "password";
        String hashedPassword = e.hashPassword(textPassword);
        System.out.println("Hashed Password 1: " + hashedPassword);
        hashedPassword = e.hashPassword(textPassword);
        System.out.println("Hashed Password 2: " + hashedPassword);

        assertEquals("hashPasswordTest", true, e.match(textPassword,hashedPassword));
    }

}
