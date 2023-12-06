package com.astrabank.service;

import java.util.List;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.Transaction;
import com.astrabank.requestData.TransactionRequest;
import com.astrabank.responseModel.GeneralResponse;

public interface TransactionService {

	public GeneralResponse sendMoney(TransactionRequest trnRequest) throws GeneralException;
	public Account updateBalance(Account account,long amount, String type);
	public List<Transaction> getAllTransactions() throws GeneralException;
}
