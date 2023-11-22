package com.artx.artx.common.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@Profile(value = "prod")
@Configuration
public class EmailConfiguration {

	@Value("${spring.mail.protocol}")
	private String protocol;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${proxy.host}")
	private String PROXY_HOST;

	@Value("${proxy.port}")
	private String PROXY_PORT;



	@Bean
	public MailSender mailSender() throws UnknownHostException {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setProtocol(protocol);
		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		//Get email properties and set
		javaMailSender.setJavaMailProperties(getMailProperties(host));
		return javaMailSender;
	}

	private Properties getMailProperties(String smtpHost) throws UnknownHostException{
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "false");
		String proxyHost = PROXY_HOST;
		String proxyPort = PROXY_PORT;
		//Add Proxy to Java Mail for a public smtp server
		if(isValidPublicIp(smtpHost)) {
			if (proxyHost != null && !proxyHost.isEmpty() && proxyPort != null && !proxyPort.isEmpty()) {
				properties.setProperty("mail.protocol.proxy.host", proxyHost);
				properties.setProperty("mail.protocol.proxy.port", proxyPort);
			}
		}

		return properties;
	}

	public boolean isValidPublicIp(String ip) throws UnknownHostException{
		InetAddress address;
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException exception) {
			throw exception;
		}
		return !(address.isSiteLocalAddress() ||
				address.isAnyLocalAddress()  ||
				address.isLinkLocalAddress() ||
				address.isLoopbackAddress() ||
				address.isMulticastAddress());
	}
}
