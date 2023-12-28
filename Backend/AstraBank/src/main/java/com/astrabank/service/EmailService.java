package com.astrabank.service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.EmailBody;
import com.astrabank.model.OtpVerification;
import com.astrabank.requestData.RequestEmailorOtp;
import com.astrabank.responseModel.GeneralResponse;

public interface EmailService {

	public boolean SendEmail(EmailBody emailBody) throws GeneralException;
	public GeneralResponse EmailVerification(RequestEmailorOtp userEmailorOtp) throws GeneralException;
	public GeneralResponse verifyEmailOtp(RequestEmailorOtp userEmailorOtp)throws GeneralException;
}
