package com.astrabank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Transaction;
import com.astrabank.requestData.TransactionRequest;
import com.astrabank.responseModel.GeneralResponse;
import com.astrabank.service.TransactionServiceImpl;

@RestController
@CrossOrigin
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl trnService;

	@PutMapping("/auth/send-money")
	public ResponseEntity<GeneralResponse> sendMoney(@RequestBody TransactionRequest trnRequest) throws GeneralException{
		GeneralResponse response = trnService.sendMoney(trnRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/auth/transactions")
	public ResponseEntity<List<Transaction>> getAllTransactions() throws GeneralException{
		List<Transaction> response = trnService.getAllTransactions();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
