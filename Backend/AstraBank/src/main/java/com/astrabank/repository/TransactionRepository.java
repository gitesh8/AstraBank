package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astrabank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
