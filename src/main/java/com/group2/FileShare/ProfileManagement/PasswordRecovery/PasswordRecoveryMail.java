package com.group2.FileShare.ProfileManagement.PasswordRecovery;

public class PasswordRecoveryMail implements IMail {

    private String recipient;
    private String subject;
    private String text;

    public PasswordRecoveryMail(String recipientEmail, String password){
        this.recipient = recipientEmail;
        this.subject = "FileShare App - Password Recovery";
        this.text = "Your FileShare password has been reset. \n"+
                "Your temporary password is: " + password + "\n"+
                "Please log in to the application and update your password in the Profile settings.";
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getRecipient() {
        return  recipient;
    }
}



