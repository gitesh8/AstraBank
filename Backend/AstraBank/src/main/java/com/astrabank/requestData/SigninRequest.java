package com.astrabank.requestData;

import java.time.LocalDate;

import com.astrabank.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {

	private String userName;
	private String password;
}
