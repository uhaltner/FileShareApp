package com.group2.FileShare;

import org.springframework.beans.factory.annotation.Value;

//TODO: Returned value is always null...

public class MailProperties {

    @Value("${mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    public String password;

    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
    private String ssl_trust;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtp_auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String smtp_tls_enable;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return System.getProperty("spring.mail.username");
    }

    public String getPassword() {
        return password;
    }

    public String getSmtp_auth() {
        return smtp_auth;
    }

    public String getSmtp_tls_enable() {
        return smtp_tls_enable;
    }

    public String getSsl_trust() {
        return ssl_trust;
    }
}
