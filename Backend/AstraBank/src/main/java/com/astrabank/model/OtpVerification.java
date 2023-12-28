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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OtpVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer otpId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String otp;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String accountUserName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean otpVerfied;
	
	private String otpVerificationId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private LocalDateTime timestamp = LocalDateTime.now();
}
