package com.astrabank.service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Card;
import com.astrabank.requestData.SetCardPinRequest;
import com.astrabank.responseModel.GeneralResponse;

public interface CardService {

	public GeneralResponse checkCardAlloted();
	public Card generateCardOrViewCard() throws GeneralException;
	public GeneralResponse setCardPin(SetCardPinRequest pin) throws GeneralException;
	public GeneralResponse changeCardStatus() throws GeneralException;
	
}
