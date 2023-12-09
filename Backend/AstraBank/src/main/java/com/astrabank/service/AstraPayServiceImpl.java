package com.astrabank.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.AccountOrCardStatus;
import com.astrabank.model.AstraPayTransaction;
import com.astrabank.model.Card;
import com.astrabank.model.Transaction;
import com.astrabank.model.TransactionStatus;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.AstraPayRepository;
import com.astrabank.repository.CardRepository;
import com.astrabank.repository.TransactionRepository;
import com.astrabank.requestData.AstraPayCardDetails;
import com.astrabank.responseModel.GeneralResponse;

@Service
public class AstraPayServiceImpl implements AstraPayService {
	

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private CardRepository cardRepo;

	@Autowired
	private AstraPayRepository astraPayRepo;
	
	@Autowired
	private TransactionServiceImpl trnService;
	
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
		astrapayid.setAmount(userCardDetails.getAmount());
		
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
		
		// checking if transaction already completed
		if(trnDetails.getStatus()!=TransactionStatus.Pending) {
			throw new GeneralException("Page Expired");
		}
		
		
		return trnDetails;
	}

	@Override
	public GeneralResponse processCardTransaction(AstraPayCardDetails userPinAndTid) throws GeneralException {
		// TODO Auto-generated method stub
		
		// getting the transaction details from transactionId;
		AstraPayTransaction userTrn =  astraPayRepo.findTransactionDetailsById(userPinAndTid.getTransactionId());
		
		//checking if it is found
		if(userTrn==null) {
			throw new GeneralException("Invalid Transaction Details");
		}

		// getting the user account
		
		Account trnAccount = accountRepo.findAccountByAccountNumber(userTrn.getUserAccountNumber());
		
		// checking the account is deactivated or not 
		if(trnAccount.getStatus()!=AccountOrCardStatus.Active) {
			
			// setting transaction status to failed
			userTrn.setStatus(TransactionStatus.Failed);
			userTrn.setRemark("Card holder is account is deactive");
			
			
			// saving the transactions 
			astraPayRepo.save(userTrn);
			
			throw new GeneralException("Card holder is account is deactive");
		}
		
		// checking the card account is deactivated or not 
		if(trnAccount.getCard().getStatus()!=AccountOrCardStatus.Active) {
			
			// setting transaction status to failed
			userTrn.setStatus(TransactionStatus.Failed);
			userTrn.setRemark("Card is deactive");
			
			
			// saving the transactions 
			astraPayRepo.save(userTrn);
			
			throw new GeneralException("Card is deactive");
		}
		
		// checking if the pin is correct 
		if(!trnAccount.getCard().getPin().equals(userPinAndTid.getPin())) {
			
			// setting transaction status to failed
			userTrn.setStatus(TransactionStatus.Failed);
			userTrn.setRemark("Invalid card pin entered");
			
			
			// saving the transactions 
			astraPayRepo.save(userTrn);
			
			throw new GeneralException("Invalid card pin entered");
		}
		
		// checking the sufficient amount is present or not 
		
		if(trnAccount.getBalance()<userTrn.getAmount()) {
			
			// setting transaction status to failed
			userTrn.setStatus(TransactionStatus.Failed);
			userTrn.setRemark("Insufficient balance");
			
			
			// saving the transactions 
			astraPayRepo.save(userTrn);
			
			throw new GeneralException("Insufficient balance");
		}
		
		// deducting balance from user
		trnService.updateBalance(trnAccount, userTrn.getAmount(), "Debit");
		
		
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(userTrn.getAmount());
		userTransaction.setFromAccountNumber(trnAccount.getAccountNumber());
		userTransaction.setRemark("Card ending "+userTrn.getUserCardNumber().substring(userTrn.getUserCardNumber().length()-4));
		userTransaction.setToAccountNumber("Astra Pay");
		userTransaction.setTransactionMode(TransactionStatus.CARD);
		userTransaction.setTransactionStatus(TransactionStatus.Success);
		userTransaction.setTransactionType(TransactionStatus.Debit);
		
		// setting transaction status to success
		userTrn.setStatus(TransactionStatus.Success);
		
		// mapping the transaction to the user 
		trnAccount.getTransaction().add(userTransaction);
		
		// saving account
		accountRepo.save(trnAccount);
		
		GeneralResponse response = new GeneralResponse();
		response.setMessage("Transaction Successfull");;
		
		return response;
		
	}

}
