package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astrabank.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

}
