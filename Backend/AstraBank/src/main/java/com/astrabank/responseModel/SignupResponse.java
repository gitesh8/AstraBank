package com.astrabank.responseModel;

import java.util.List;

import com.astrabank.model.AccountOrCardStatus;
import com.astrabank.model.AccountType;
import com.astrabank.model.Customer;
import com.astrabank.model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponse {

	private boolean status;
	private String AccountNumber;
	private String userName;
	private String AccountHolderName;
	private String bankName;
	private String message;
	
	public SignupResponse() {
		this.status=true;
		this.bankName="Astra Bank";
		this.message="Congratulation, Your Account is Created Successfully with Astra Bank";
	}
	
}
