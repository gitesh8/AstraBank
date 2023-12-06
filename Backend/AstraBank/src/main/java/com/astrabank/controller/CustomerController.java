package com.astrabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.AuthenticationFailedException;
import com.astrabank.requestData.SigninRequest;
import com.astrabank.requestData.SignupRequest;
import com.astrabank.responseModel.JwtTokenResponse;
import com.astrabank.responseModel.SignupResponse;
import com.astrabank.service.CustomerServiceImpl;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) throws AuthenticationFailedException{
		SignupResponse response = customerService.signup(signupRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtTokenResponse> signin(@RequestBody SigninRequest signinRequest){
		JwtTokenResponse response = customerService.signin(signinRequest.getUserName(), signinRequest.getPassword());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
