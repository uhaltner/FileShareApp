package com.group2.FileShare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultProperties {

	private static DefaultProperties properties;
	private Properties defaultProperties;

	private DefaultProperties() {
		defaultProperties = new Properties();
		try {
			String dbFile = "default.properties";
			InputStream inputDB = getClass().getClassLoader().getResourceAsStream(dbFile);
			defaultProperties.load(inputDB);
		} catch (IOException e) {
			System.out.print("Error connecting file of Database Properties");
		}
	}

	public static DefaultProperties getInstance() {
		if (properties == null) {
			properties = new DefaultProperties();
		}
		return properties;
	}

	public String getJDBCConnectionString() {
		if (System.getProperty("JDBC_CONNECTION_STRING") == null || System.getProperty("JDBC_CONNECTION_STRING").isEmpty()) {
			return defaultProperties.getProperty("JDBC_CONNECTION_STRING");
		} else {
			return System.getProperty("JDBC_CONNECTION_STRING");
		}
	}

	public String getS3BucketName() {
		if (System.getProperty("FILESHARE_S3_BUCKET_NAME") == null || System.getProperty("FILESHARE_S3_BUCKET_NAME").isEmpty()) {
			return defaultProperties.getProperty("S3_BUCKET_NAME");
		} else {
			return System.getProperty("FILESHARE_S3_BUCKET_NAME");
		}
	}

	public long getS3LinkValidityMillis() {
		return Long.parseLong(defaultProperties.getProperty("S3_LINK_VALIDITY_MILLIS"));
	}

	public String getMailHost(){
		return defaultProperties.getProperty("mail_host");
	}

	public int getMailPort(){
		return Integer.parseInt(defaultProperties.getProperty("mail_port"));
	}

	public String getMailUsername(){
		return defaultProperties.getProperty("mail_username");
	}

	public String getMailPassword(){
		return defaultProperties.getProperty("mail_password");
	}

	public String getMailSmtpSSLTrust(){
		return defaultProperties.getProperty("mail_properties_mail_smtp_ssl_trust");
	}

	public String getMailSmtpAuth(){
		return defaultProperties.getProperty("mail_properties_mail_smtp_auth");
	}

	public String getMailSmtpStartTlsEnable(){
		return defaultProperties.getProperty("mail_properties_mail_smtp_starttls_enable");
	}

	public String getMailTransportProtocol(){
		return defaultProperties.getProperty("mail_transport_protocol");
	}

	public String getMailDebug(){
		return defaultProperties.getProperty("mail_debug");
	}

	public String[] getInvalidMimePrefixes() {
		return defaultProperties.getProperty("FILE_UPLOAD.invalidMimePrefixes").split(",");
	}
	
	public Long getSizeLimitInBytes() {
		return Long.parseLong(defaultProperties.getProperty("FILE_UPLOAD.sizeLimitInBytes"));
	}
	
	public Long getStorageSizeLimitInBytes() {
		return Long.parseLong(defaultProperties.getProperty("FILE_UPLOAD.storageSizeLimitInBytes"));
	}


}
