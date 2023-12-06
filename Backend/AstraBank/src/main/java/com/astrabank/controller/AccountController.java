package com.astrabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.model.Account;
import com.astrabank.service.AccountServiceImpl;

@RestController
public class AccountController {

	@Autowired
	private AccountServiceImpl accountService;
	
	@PostMapping("/auth/dashboard")
	public ResponseEntity<Account> dashboard(){
		Account response = accountService.dashboard();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
