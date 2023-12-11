package com.astrabank.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.AuthenticationFailedException;
import com.astrabank.requestData.SigninRequest;
import com.astrabank.requestData.SignupRequest;
import com.astrabank.responseModel.JwtTokenResponse;
import com.astrabank.responseModel.SignupResponse;
import com.astrabank.service.CustomerServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) throws AuthenticationFailedException{
		SignupResponse response = customerService.signup(signupRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtTokenResponse> signin(@Valid @RequestBody SigninRequest signinRequest){
		JwtTokenResponse response = customerService.signin(signinRequest.getUserName(), signinRequest.getPassword());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/start-server")
	public ResponseEntity<String> start_server(){
		return new ResponseEntity<>("Server Started",HttpStatus.OK);
	}

}
