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
public class SignupRequest {

	private String firstName;
	private String lastName;
	private AccountType accountType;
	private LocalDate dateOfBirth;
	private String username;
	private String password;
}
