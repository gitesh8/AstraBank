package com.astrabank.service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.AstraPayTransaction;
import com.astrabank.requestData.AstraPayCardDetails;
import com.astrabank.responseModel.GeneralResponse;

public interface AstraPayService {

	public AstraPayTransaction generateTransactionId(AstraPayCardDetails userCardDetails)  throws GeneralException;
	public AstraPayTransaction getTransactionId(String TransactionId) throws GeneralException;
	public GeneralResponse processCardTransaction(AstraPayCardDetails userPinAndTid) throws GeneralException;
	
}
