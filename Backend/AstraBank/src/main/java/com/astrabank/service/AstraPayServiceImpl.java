package com.astrabank.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.AstraPayTransaction;
import com.astrabank.model.Card;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.AstraPayRepository;
import com.astrabank.repository.CardRepository;
import com.astrabank.repository.TransactionRepository;
import com.astrabank.requestData.AstraPayCardDetails;

@Service
public class AstraPayServiceImpl implements AstraPayService {
	
	@Autowired
	private TransactionRepository trnRepo;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private CurrentLoggedinUser userName;
	
	@Autowired
	private CardRepository cardRepo;

	@Autowired
	private AstraPayRepository astraPayRepo;
	
	@Override
	public AstraPayTransaction generateTransactionId(AstraPayCardDetails userCardDetails) throws GeneralException {
		// TODO Auto-generated method stub
		
		// getting card detail 
		Card userCard = cardRepo.findCardbyCardNumber(userCardDetails.getCardNumber());
		
		// check if the card number is exists or not 
		if(userCard==null) {
			throw new GeneralException("Invalid Card Details");
		}
		
		// checking expiry and cvv is correct or not 
		
		if(!userCard.getCvv().equals(userCardDetails.getCvv()) && !userCard.getExpiry().equals(userCardDetails.getCvv())) {
			throw new GeneralException("Incorrect Cvv or Expiry");
		}
		
		// Account Number of User
		String userAccountNumber = userCard.getCardHolderAccount().getAccountNumber();
		
		// generating astrpay transaction object
		
		AstraPayTransaction astrapayid = new AstraPayTransaction();
		astrapayid.setAccountNumberLast4digits(userAccountNumber.substring(userAccountNumber.length()-4));
		astrapayid.setTransactionId(UUID.randomUUID().toString());
		astrapayid.setUserAccountNumber(userAccountNumber);
		astrapayid.setUserCardNumber(userCard.getCardNumber());
		
		// saving to database 
		astraPayRepo.save(astrapayid);
		
		return astrapayid;
	}

	@Override
	public AstraPayTransaction getTransactionId(String TransactionId) throws GeneralException {
		// TODO Auto-generated method stub
		
		// getting transaction object 
		
		AstraPayTransaction trnDetails = astraPayRepo.findTransactionDetailsById(TransactionId);
		
		// checking it is null or not 
		
		if(trnDetails==null) {
			throw new GeneralException("Invalid Transaction");
		}
		
		return trnDetails;
	}

}
