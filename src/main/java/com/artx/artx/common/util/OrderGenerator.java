package com.artx.artx.common.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		return "ORD-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + generatorRandomSuffix();
	}

	public String generatorRandomSuffix(){
		Random random = new Random();
		int suffix = 10000000 + random.nextInt(90000000);
		return String.valueOf(suffix);
	}

}
