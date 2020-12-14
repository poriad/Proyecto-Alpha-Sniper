package org.iesalixar.poriad.dto;

import lombok.Data;

@Data
public class PaymentIntentDTO {

	public enum Currency {
		USD, EUR;
	}

	private String description;
	private int amount;
	private Currency currency;

}
