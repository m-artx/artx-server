package com.artx.artx.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Profile("prod")
@Configuration
public class ProxyRestTemplateConfig {

	@Value("${proxy.host}")
	private String PROXY_HOST;

	@Value("${proxy.port}")
	private int PROXY_PORT;

	@Bean
	public RestTemplate restTemplate(){

		RestTemplate restTemplate = new RestTemplate();
		if(PROXY_HOST != null && !PROXY_HOST.equals("") && PROXY_PORT != 0){
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));
			requestFactory.setProxy(proxy);
			restTemplate.setRequestFactory(requestFactory);
		}


		return restTemplate;
	}

}
