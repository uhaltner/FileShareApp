package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import org.springframework.mail.SimpleMailMessage;

public interface IMail {

    public String getRecipient();
    public String getSubject();
    public String getText();

}
