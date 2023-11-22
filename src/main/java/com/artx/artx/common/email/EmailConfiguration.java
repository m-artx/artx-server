//package com.artx.artx.common.email;
//
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.net.UnknownHostException;
//import java.util.Properties;
//
//@Profile(value = "prod")
//@Configuration
//public class EmailConfiguration {
//
//	@Value("${spring.mail.protocol}")
//	private String protocol;
//
//	@Value("${spring.mail.host}")
//	private String host;
//
//	@Value("${spring.mail.port}")
//	private int port;
//
//	@Value("${spring.mail.username}")
//	private String username;
//
//	@Value("${spring.mail.password}")
//	private String password;
//
//	@Value("${proxy.host}")
//	private String proxyHost;
//
//	@Value("${proxy.port}")
//	private String proxyPort;
//
//	@Bean
//	public JavaMailSender mailSender() {
//
//
//
//	}
//}
