package com.astrabank.requestData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AstraPayCardDetails {

	private String cardNumber;
	private String cvv;
	private String expiry;
	private long amount;
	private String pin;
}
