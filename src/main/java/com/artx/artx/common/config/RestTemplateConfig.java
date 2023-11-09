package com.artx.artx.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class RestTemplateConfig {

	@Value("${proxy.host}")
	private String proxyHost;

	@Value("${proxy.port}")
	private int proxyPort;



	@Bean
	public RestTemplate restTemplate(){

		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		String proxyHost = "http://krmp-proxy.9rum.cc";
		int proxyPort = 3128;

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		requestFactory.setProxy(proxy);
		restTemplate.setRequestFactory(requestFactory);
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}

}
