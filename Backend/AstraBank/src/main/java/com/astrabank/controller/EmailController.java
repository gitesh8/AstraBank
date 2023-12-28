package com.astrabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.GeneralException;
import com.astrabank.requestData.RequestEmailorOtp;
import com.astrabank.responseModel.GeneralResponse;
import com.astrabank.service.EmailServiceImpl;

@RestController
@CrossOrigin
public class EmailController {

	@Autowired
	private EmailServiceImpl emailService;
	
	@PutMapping("/email/email-verification")
	public ResponseEntity<GeneralResponse> emailVerification(@RequestBody RequestEmailorOtp emailOrOtp) throws GeneralException{
	  GeneralResponse response =  emailService.EmailVerification(emailOrOtp);
	  return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("/email/verify-email-otp")
	public ResponseEntity<GeneralResponse> verifyEmailOtp(@RequestBody RequestEmailorOtp emailOrOtp) throws GeneralException{
	  GeneralResponse response =  emailService.verifyEmailOtp(emailOrOtp);
	  return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
