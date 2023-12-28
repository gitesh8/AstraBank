package com.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astrabank.model.OtpVerification;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {

	@Query("From OtpVerification where otpVerificationId=?1 AND accountUserName=?2")
	public OtpVerification findOtpByVerficationId(String otpVerificationId, String accUserName);
}
