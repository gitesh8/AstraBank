package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.astrabank.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

	@Query("From Card where cardNumber=?1")
	public Card findCardbyCardNumber(String CardNumber);
}
