package com.astrabank.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	public Transaction() {
		this.timestamp= LocalDateTime.now();
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fromAccountNumber;
	private String toAccountNumber;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private LocalDateTime timestamp;
	private String remark;
	
	private TransactionStatus transactionType;
	private TransactionStatus transactionStatus;
	private TransactionStatus transactionMode;
	
	
}
