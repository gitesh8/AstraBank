package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.astrabank.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("From Account where username=?1")
	public Account findAccountByUsername(String username);
	
	@Query("From Account where accountNumber=?1")
	public Account findAccountByAccountNumber(String accountNumber);
	
}
