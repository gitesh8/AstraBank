package com.astrabank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.Transaction;
import com.astrabank.model.TransactionStatus;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.TransactionRepository;
import com.astrabank.requestData.TransactionRequest;
import com.astrabank.responseModel.GeneralResponse;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository trnRepo;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private CurrentLoggedinUser userName;

	private static final Object lock = new Object();

	@Override
	@Transactional
	public GeneralResponse sendMoney(TransactionRequest trnRequest) throws GeneralException {
		// TODO Auto-generated method stub

		synchronized (lock) {
			// getting user account
			Account SenderAccount = accountRepo.findAccountByUsername(userName.getLoggedInUser());

			// checking if the account has the balance
			if (SenderAccount.getBalance() < trnRequest.getAmount()) {
				throw new GeneralException("Insufficient Funds");
			}

			// getting the account to receiverUser

			Account receiverAccount = accountRepo.findAccountByAccountNumber(trnRequest.getAccountNumber());

			if (receiverAccount == null) {
				throw new GeneralException("Account number does not exists");
			}

			// creating transaction record for Sender Account

			Transaction senderTrn = new Transaction();
			senderTrn.setFromAccountNumber(SenderAccount.getAccountNumber());
			senderTrn.setRemark(trnRequest.getRemark());
			senderTrn.setToAccountNumber(receiverAccount.getAccountNumber());
			senderTrn.setTransactionMode(TransactionStatus.IMPS);
			senderTrn.setTransactionStatus(TransactionStatus.Success);
			senderTrn.setTransactionType(TransactionStatus.Debit);

			// creating transaction record for Receiver Account

			Transaction receiverTrn = new Transaction();
			receiverTrn.setFromAccountNumber(SenderAccount.getAccountNumber());
			receiverTrn.setRemark(trnRequest.getRemark());
			receiverTrn.setToAccountNumber(receiverAccount.getAccountNumber());
			receiverTrn.setTransactionMode(TransactionStatus.IMPS);
			receiverTrn.setTransactionStatus(TransactionStatus.Success);
			receiverTrn.setTransactionType(TransactionStatus.Credit);

			// updating balance of both accounts
			Account updateSenderAccount = updateBalance(SenderAccount, trnRequest.getAmount(), "Debit");
			Account updateReceiverAccount = updateBalance(receiverAccount, trnRequest.getAmount(), "Credit");

			// mapping transaction to sender account
			updateSenderAccount.getTransaction().add(senderTrn);

			// mapping transaction to receiver account
			updateReceiverAccount.getTransaction().add(receiverTrn);

			// saving both the accounts
			accountRepo.save(updateSenderAccount);
			accountRepo.save(receiverAccount);

			GeneralResponse response = new GeneralResponse();
			response.setMessage("Transaction Successfull");

			return response;
		}

	}

	@Override
	public Account updateBalance(Account account, long amount, String type) {
		// TODO Auto-generated method stub

		if (type.equals("Debit")) {
			account.setBalance(account.getBalance() - amount);
			return account;
		} else {
			account.setBalance(account.getBalance() + amount);
			return account;
		}
	}

	@Override
	public List<Transaction> getAllTransactions() throws GeneralException {
		// TODO Auto-generated method stub
		
		// getting user account
		Account account = accountRepo.findAccountByUsername(userName.getLoggedInUser());
		
		// checking the transaction is empty
		
		if(account.getTransaction().size()==0) {
			throw new GeneralException("No Transactions Found");
		}
		
		return account.getTransaction();
	}

}
