package com.group2.FileShare.ProfileManagement;

/**
 * https://www.mkyong.com/spring-security/spring-security-password-hashing-example/
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder implements IPasswordEncoder {

    @Override
    public String hashPassword(String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        return hashedPassword;
    }

    @Override
    public boolean match(String password, String hashedPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(hashedPassword.equals(passwordEncoder.encode(password))){
            return true;
        }

        return false;
    }

}
