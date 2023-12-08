package com.astrabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Card;
import com.astrabank.requestData.SetCardPinRequest;
import com.astrabank.responseModel.GeneralResponse;
import com.astrabank.service.CardServiceImpl;

@RestController
@CrossOrigin
public class CardController {

	@Autowired
	private CardServiceImpl cardService;
	
	@GetMapping("/auth/card")
	public ResponseEntity<GeneralResponse> checkCardAlloted(){
		GeneralResponse response = cardService.checkCardAlloted();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/auth/card/view-card-or-new")
	public ResponseEntity<Card> generateCard() throws GeneralException{
		Card response = cardService.generateCardOrViewCard();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("/auth/card/set-pin")
	public ResponseEntity<GeneralResponse> setPin(@RequestBody SetCardPinRequest pin) throws GeneralException{
		GeneralResponse response = cardService.setCardPin(pin);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/auth/card/change-status")
	public ResponseEntity<GeneralResponse> changeStatus() throws GeneralException{
		GeneralResponse response = cardService.changeCardStatus();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
