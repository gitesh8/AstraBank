package com.astrabank.service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Card;
import com.astrabank.responseModel.GeneralResponse;

public interface CardService {

	public GeneralResponse checkCardAlloted();
	public Card generateCard() throws GeneralException;
	
}
