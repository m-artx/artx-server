package com.artx.artx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArtxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtxApplication.class, args);
	}

}
