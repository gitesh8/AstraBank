package com.astrabank.service;

import com.astrabank.exception.AuthenticationFailedException;
import com.astrabank.requestData.SignupRequest;
import com.astrabank.responseModel.JwtTokenResponse;
import com.astrabank.responseModel.SignupResponse;

public interface CustomerService {

	public SignupResponse signup(SignupRequest signupRequest) throws AuthenticationFailedException;
	public JwtTokenResponse signin(String username, String password);
}
