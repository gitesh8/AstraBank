package com.astrabank.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.Card;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.CardRepository;
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
	public Card generateCard() throws GeneralException {
		// TODO Auto-generated method stub
		
		
		// if the card is already alloted
		if (checkCardAlloted().getMessage().equals("Yes")) {
			throw new GeneralException("Only one card allowed for one account");
		}
		
		// fetching user account 
		Account userAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// Get current timestamp
        long timestamp = System.currentTimeMillis();
		
		// generating card number with timestamp 
		String formattedTimestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(timestamp));
		
		// Generate 4 digit random numbers
        Random random = new Random();
        int randomNumbers = 1000 + random.nextInt(9000);
        
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
        
        
        // mapping account to card
        userCard.setCardHolderAccount(userAccount);
        
        // mapping card to account
        userAccount.setCard(userCard);
        
        // saving both the object
        Account savedAccount = accountRepo.save(userAccount);
        
        return savedAccount.getCard();
        
	}

}
