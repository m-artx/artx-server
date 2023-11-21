package com.artx.artx.common.model;

import com.artx.artx.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

	@Test
	@DisplayName("금액을 더한다.")
	void successAddAmount(){
		Money money1 = new Money(BigDecimal.valueOf(1000), Currency.KRW);
		Money money2 = new Money(BigDecimal.valueOf(2000), Currency.KRW);

		Money newMoney = money1.add(money2);

		assertEquals(newMoney.getAmount().intValue(), 3000);
	}

	@Test
	@DisplayName("금액을 차감한다.")
	void successSubtractAmount(){
		Money money1 = new Money(BigDecimal.valueOf(4000), Currency.KRW);
		Money money2 = new Money(BigDecimal.valueOf(2000), Currency.KRW);

		Money newMoney = money1.subtract(money2);

		assertEquals(newMoney.getAmount().intValue(), 2000);
	}

	@Test
	@DisplayName("금액을 더했을 때 통화가 다르면 에러가 난다.")
	void failAddAmountWithDifferentCurrency(){
		Money money1 = new Money(BigDecimal.valueOf(1000), Currency.KRW);
		Money money2 = new Money(BigDecimal.valueOf(2000), Currency.EUR);

		assertThrows(BusinessException.class, () -> money1.add(money2));
	}

	@Test
	@DisplayName("금액을 차감했을 때 통화가 다르면 에러가 난다.")
	void failsubtractAmountWithDifferentCurrency(){
		Money money1 = new Money(BigDecimal.valueOf(4000), Currency.KRW);
		Money money2 = new Money(BigDecimal.valueOf(2000), Currency.EUR);

		assertThrows(BusinessException.class, () -> money1.subtract(money2));
	}

	@Test
	@DisplayName("기존 금액보다 더 큰 금액을 차감했을 때 에러가 난다.")
	void failSubtractAmountMoreThanZero(){
		Money money1 = new Money(BigDecimal.valueOf(1000), Currency.KRW);
		Money money2 = new Money(BigDecimal.valueOf(2000), Currency.KRW);

		assertThrows(BusinessException.class, () -> money1.subtract(money2));
	}

}
