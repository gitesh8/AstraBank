package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.astrabank.model.AstraPayTransaction;

public interface AstraPayRepository extends JpaRepository<AstraPayTransaction, Integer> {

	@Query("From AstraPayTransaction where transactionId=?1")
	public AstraPayTransaction findTransactionDetailsById(String transactionId);
}
