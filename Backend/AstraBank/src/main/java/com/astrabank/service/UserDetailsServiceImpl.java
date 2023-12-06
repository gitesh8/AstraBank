package com.astrabank.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.astrabank.model.Account;
import com.astrabank.repository.AccountRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		// finding account by username
		Account account = accountRepo.findAccountByUsername(username);
		
		// checking whether account is null or not
		
		if(account==null) {
			throw new  UsernameNotFoundException("Invalid Credientials");
		}
		else {
			return new User(username, account.getPassword(), new ArrayList<>());
		}
	}

}
