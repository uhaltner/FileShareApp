package com.group2.FileShare;

import com.group2.FileShare.ProfileManagement.PasswordRecovery.IMail;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailService implements IMailService{

    @Override
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

        mailSender.setHost(DefaultProperties.getInstance().getMailHost());
        mailSender.setPort(DefaultProperties.getInstance().getMailPort());

        mailSender.setUsername(DefaultProperties.getInstance().getMailUsername());
        mailSender.setPassword(DefaultProperties.getInstance().getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", DefaultProperties.getInstance().getMailTransportProtocol());
        props.put("mail.smtp.ssl.trust", DefaultProperties.getInstance().getMailSmtpSSLTrust());
        props.put("mail.smtp.auth", DefaultProperties.getInstance().getMailSmtpAuth());
        props.put("mail.smtp.starttls.enable", DefaultProperties.getInstance().getMailSmtpStartTlsEnable());
        props.put("mail.debug", DefaultProperties.getInstance().getMailDebug());

        return mailSender;
    }

}
