package com.astrabank.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astrabank.config.JwtUtil;
import com.astrabank.exception.AuthenticationFailedException;
import com.astrabank.model.Account;
import com.astrabank.model.Customer;
import com.astrabank.model.Transaction;
import com.astrabank.model.TransactionStatus;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.CustomerRepository;
import com.astrabank.repository.TransactionRepository;
import com.astrabank.requestData.SignupRequest;
import com.astrabank.responseModel.JwtTokenResponse;
import com.astrabank.responseModel.SignupResponse;

@Service
public class CustomerServiceImpl implements CustomerService {
	
    
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public SignupResponse signup(SignupRequest signupRequest) throws AuthenticationFailedException {
		// TODO Auto-generated method stub
		
		// checking the username exists or not 
		
		if(accountRepo.findAccountByUsername(signupRequest.getUsername())!=null) {
			throw new AuthenticationFailedException("Username Already Exists, Please Try Again");
		}
		
		// creating customer account 
		
		Customer cutomerSignup = new Customer();
		cutomerSignup.setFirstName(signupRequest.getFirstName());
		cutomerSignup.setLastName(signupRequest.getLastName());
		cutomerSignup.setDob(signupRequest.getDateOfBirth());
		
		// creating account number 
		long currentTime = System.currentTimeMillis();
		
		// generating 2 random digit
		Random random = new Random();
		int randomValue = random.nextInt(90)+10;
		
	     // Combine current time and random number to create a 7-digit unique number
        long finalAccountNumber = currentTime * 100 + randomValue;
		
		// Creating Account for Customer
		Account customerAccount = new Account();
	    customerAccount.setAccountType(signupRequest.getAccountType());
	    customerAccount.setUsername(signupRequest.getUsername());
	    customerAccount.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
	    customerAccount.setAccountNumber(Long.toString(finalAccountNumber));
	    
	    
	    // creating initial transaction record 
	    Transaction initialTrn = new Transaction();
	    initialTrn.setFromAccountNumber("Astra Bank");
	    initialTrn.setToAccountNumber(Long.toString(finalAccountNumber));
	    initialTrn.setRemark("Account Opening Gift From Astra Bank");
	    initialTrn.setTransactionMode(TransactionStatus.IMPS);
	    initialTrn.setTransactionStatus(TransactionStatus.Success);
	    initialTrn.setTransactionType(TransactionStatus.Credit);
	    
	    // updating user balance 
	    customerAccount.setBalance(1000);
	    
	    // mapping customer details to account;
	    
	    cutomerSignup.getAccounts().add(customerAccount);
	    
	    // mapping account to customer
	    customerAccount.setCustomer(cutomerSignup);
	    
	    // mapping transaction to user Account
	    customerAccount.getTransaction().add(initialTrn);
	    
	    // saving all details
	    customerRepo.save(cutomerSignup);
	    accountRepo.save(customerAccount);
	    transactionRepo.save(initialTrn);
	    
	    
	    // creating signupResponse
	    SignupResponse response = new  SignupResponse();
	    response.setAccountHolderName(signupRequest.getFirstName()+" "+signupRequest.getLastName());
	    response.setAccountNumber(Long.toString(finalAccountNumber));
	    response.setUserName(signupRequest.getUsername());
	    
	   
		return response;
	}

	@Override
	public JwtTokenResponse signin(String username, String password) {
		// TODO Auto-generated method stub
		
		// checking username and password
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		
		// generating token 
		
		String jwtToken = jwtUtil.generateToken(username);
		
		JwtTokenResponse response = new JwtTokenResponse();
		response.setJwtToken(jwtToken);
		
		return response;
	}

}
