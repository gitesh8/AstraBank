package com.astrabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.AstraPayTransaction;
import com.astrabank.requestData.AstraPayCardDetails;
import com.astrabank.responseModel.GeneralResponse;
import com.astrabank.service.AstraPayServiceImpl;

@RestController
@CrossOrigin
public class AstraPayController {

	@Autowired
	private AstraPayServiceImpl astraPayService;
	
	@PostMapping("/astrapay/card")
	public ResponseEntity<AstraPayTransaction> generateTransaction(@RequestBody AstraPayCardDetails userCardDetails) throws GeneralException{
		AstraPayTransaction response = astraPayService.generateTransactionId(userCardDetails);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/astrapay/card")
	public ResponseEntity<AstraPayTransaction> trnDetails(@RequestParam("trnId") String trnId) throws GeneralException{
		AstraPayTransaction response = astraPayService.getTransactionId(trnId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/astrapay/card/verify")
	public ResponseEntity<GeneralResponse> verify(@RequestBody AstraPayCardDetails userCardDetails) throws GeneralException{
		GeneralResponse response = astraPayService.processCardTransaction(userCardDetails);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
