package com.artx.artx.etc.util.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DeliveryGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		return "D-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + generatorRandomSuffix();
	}

	public String generatorRandomSuffix(){
		Random random = new Random();
		int suffix = 10000000 + random.nextInt(90000000);
		return String.valueOf(suffix);
	}
}
