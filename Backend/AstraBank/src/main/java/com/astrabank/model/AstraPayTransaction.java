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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class AstraPayTransaction {

	public AstraPayTransaction() {
		this.timestamp= LocalDateTime.now();
		this.status=TransactionStatus.Pending;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer aptId;
	
	private String transactionId;
	private TransactionStatus status;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userAccountNumber;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userCardNumber;
	
	private String accountNumberLast4digits;
	private long amount;
	
	private String remark;
	
	private LocalDateTime timestamp;
	
}
