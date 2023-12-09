package com.astrabank.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.AccountOrCardStatus;
import com.astrabank.model.Card;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.CardRepository;
import com.astrabank.requestData.SetCardPinRequest;
import com.astrabank.responseModel.GeneralResponse;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CurrentLoggedinUser userName;
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private CardRepository cardRepo;
	
	
	@Override
	public GeneralResponse checkCardAlloted() {
		// TODO Auto-generated method stub
		
		// fetching user account 
		
		Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// creating response 
		GeneralResponse response = new GeneralResponse();
		
		if(userAccount.getCard()==null) {
			response.setMessage("No");
			return response;
		}
		response.setMessage("Yes");
		return response;
		
	}


	@Override
	public Card generateCardOrViewCard() throws GeneralException {
		// TODO Auto-generated method stub
		
		
		// if the card is already alloted
		if (checkCardAlloted().getMessage().equals("Yes")) {
			Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
			return userAccount.getCard();
			
		}
		
		// fetching user account 
		Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// Get current timestamp
        long timestamp = System.currentTimeMillis();
		
		// generating card number with timestamp 
		String formattedTimestamp = new SimpleDateFormat("MMmmyyyyddssHH").format(new Date(timestamp));
		
		// Generate 4 digit random numbers
        Random random = new Random();
        int randomNumbers = 10 + random.nextInt(90);
        
        // adding timestamp and random numbers
        String cardNumber = formattedTimestamp + randomNumbers;
        
     // Generate CVV (3-digit random number)
        String cvv = String.valueOf(100 + random.nextInt(900));
        
     // Generate expiry date (valid for the next 5 years)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 5);
        Date expiryDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        String formattedExpiryDate = dateFormat.format(expiryDate);
        
        // creating card
        Card userCard = new Card();
        userCard.setCardHolderName(userAccount.getCustomer().getFirstName()+" "+userAccount.getCustomer().getLastName());
        userCard.setCardNumber(cardNumber);
        userCard.setCvv(cvv);
        userCard.setExpiry(formattedExpiryDate);
        userCard.setStatus(AccountOrCardStatus.pinNotSet);
        
        
        // mapping account to card
        userCard.setCardHolderAccount(userAccount);
        
        // mapping card to account
        userAccount.setCard(userCard);
        
        // saving both the object
        Account savedAccount = accountRepo.save(userAccount);
        
        return savedAccount.getCard();
        
	}


	@Override
	public GeneralResponse setCardPin(SetCardPinRequest pin) throws GeneralException {
		// TODO Auto-generated method stub
		
		// fetching user account 
		Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// checking if the pin already set
		if(userAccount.getCard().getPin()!=null) {
			throw new GeneralException("Pin already set");
		}
		
		// setting pin to card and changing card status
		userAccount.getCard().setPin(pin.getPin());
		userAccount.getCard().setStatus(AccountOrCardStatus.Active);
		
		
		// saving card;
		accountRepo.save(userAccount);
		
		GeneralResponse response = new GeneralResponse();
		response.setMessage("Pin Successfully Set");
		return response;
	}


	@Override
	public GeneralResponse changeCardStatus() throws GeneralException{
		// TODO Auto-generated method stub
		
		// fetching user account 
		Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// checking if the pin is set or not 
		if(userAccount.getCard().getPin()==null) {
			throw new GeneralException("Set pin to activate the card");
		}
		
		
		// changing card status vice versa
		if(userAccount.getCard().getStatus()==AccountOrCardStatus.Active) {
			userAccount.getCard().setStatus(AccountOrCardStatus.Deactive);
		}
		else {
			userAccount.getCard().setStatus(AccountOrCardStatus.Active);
		}
		
		// saving account
		accountRepo.save(userAccount);
		
		GeneralResponse response =new GeneralResponse();
		response.setMessage("Card status changed successfully");
		
		return response;
		
	}

}
