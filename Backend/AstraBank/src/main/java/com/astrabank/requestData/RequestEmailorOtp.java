package com.astrabank.requestData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmailorOtp {

	private String email;
	private String otp;
	private String otpId;
}
