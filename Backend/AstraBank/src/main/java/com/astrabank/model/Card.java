package com.astrabank.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cardId;
	
	public Card() {
		this.timestamp=LocalDateTime.now();
		this.status=AccountOrCardStatus.Deactive;
	}
	
	private String cardNumber;
	private String cardHolderName;
	private String expiry;
	private Integer pin;
	private String cvv;
	private AccountOrCardStatus status;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Account cardHolderAccount;
	
	private LocalDateTime timestamp;
}
