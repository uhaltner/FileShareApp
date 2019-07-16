package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import com.group2.FileShare.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailService {

    public void sendEmail(IMail mail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.getRecipient());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());

        getJavaMailSender().send(message);
    }

    @Bean
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        MailProperties properties = new MailProperties();

        System.out.println("Host: " + properties.getHost());
        System.out.println("Username: " + properties.getUsername());
        System.out.println("Password: " + properties.getPassword());

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("fileshare5308@gmail.com");
        mailSender.setPassword("gaapfheegfxeelhy");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

}
