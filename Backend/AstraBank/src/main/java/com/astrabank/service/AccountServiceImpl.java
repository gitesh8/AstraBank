package com.astrabank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.astrabank.model.Account;
import com.astrabank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private CurrentLoggedinUser userName;

	@Override
	public Account dashboard() {
		// TODO Auto-generated method stub
		
		
		Account account = accountRepo.findAccountByUsername( userName.getLoggedInUser());
		
		return account;
		
	}

}
