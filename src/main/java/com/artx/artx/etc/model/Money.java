package com.artx.artx.etc.model;

import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Money {

	private BigDecimal amount;
	private Currency currency;

	public Money add(Money money){
		if(this.currency != money.getCurrency()){
			throw new BusinessException(ErrorCode.DIFFERENT_CURRENCY);
		}

		return new Money(this.amount.add(money.getAmount()), money.getCurrency());
	}

	public Money subtract(Money money){
		if(!(this.amount.compareTo(money.getAmount()) > 0)){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}

		if(this.currency != money.getCurrency()){
			throw new BusinessException(ErrorCode.DIFFERENT_CURRENCY);
		}

		return new Money(this.amount.subtract(money.getAmount()), money.getCurrency());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Money money)) return false;
		return amount.equals(money.amount) && currency == money.currency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}
}
