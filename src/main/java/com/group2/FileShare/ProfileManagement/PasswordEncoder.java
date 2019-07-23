package com.group2.FileShare.ProfileManagement;

/**Reference Material:
 * https://www.mkyong.com/spring-security/spring-security-password-hashing-example/
 * https://dzone.com/articles/spring-security-with-spring-boot-20-password-encod
 */

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder implements IPasswordEncoder {

    @Override
    public String hashPassword(String rawPassword) {

        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(4));

        return hashedPassword;
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {

        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

}
