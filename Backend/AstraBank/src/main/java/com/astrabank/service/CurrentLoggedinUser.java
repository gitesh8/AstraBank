package com.astrabank.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentLoggedinUser {

	public String getLoggedInUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String userName = auth.getName();
		
		return userName;
	}
}
