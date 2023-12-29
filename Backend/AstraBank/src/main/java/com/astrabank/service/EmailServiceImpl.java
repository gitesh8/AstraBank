package com.astrabank.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.astrabank.exception.GeneralException;
import com.astrabank.model.Account;
import com.astrabank.model.AccountOrCardStatus;
import com.astrabank.model.Customer;
import com.astrabank.model.EmailBody;
import com.astrabank.model.OtpVerification;
import com.astrabank.repository.AccountRepository;
import com.astrabank.repository.CustomerRepository;
import com.astrabank.repository.OtpVerificationRepository;
import com.astrabank.requestData.RequestEmailorOtp;
import com.astrabank.responseModel.GeneralResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private CurrentLoggedinUser currentUser;
	
	@Autowired
	private OtpVerificationRepository otpRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public boolean SendEmail(EmailBody emailBody) throws GeneralException {
		// TODO Auto-generated method stub

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			mimeMessage.setContent(emailBody.getBody(), "text/html");
			helper.setTo(emailBody.getToEmail());
			helper.setSubject(emailBody.getSubject());

			emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			return false;
		}

		return true;
	}

	@Override
	public GeneralResponse EmailVerification(RequestEmailorOtp userEmailorOtp) throws GeneralException{
		// TODO Auto-generated method stub
		
		// checking if the email already exists 
		
		String email = customerRepo.findCustomerByEmail(userEmailorOtp.getEmail());
		
		if(email!=null) {
			throw new GeneralException("Email Already Exists");
		}

		// creating random otp

		SecureRandom secureRandom = new SecureRandom();

		String otp = String.valueOf(100_000 + secureRandom.nextInt(900_000));
		
		// creating random otp string
		String OtpVerificationId = UUID.randomUUID().toString();
		
		// creating otp object

		OtpVerification otpVerfication = new OtpVerification();
		otpVerfication.setAccountUserName(currentUser.getLoggedInUser());
		otpVerfication.setOtp(otp);
		otpVerfication.setOtpVerfied(false);
		otpVerfication.setOtpVerificationId(UUID.randomUUID().toString());
		otpVerfication.setOtpVerificationId(OtpVerificationId);
		otpVerfication.setEmail(userEmailorOtp.getEmail());
		

		// creating emailbody object

		EmailBody emailBody = new EmailBody();
		emailBody.setBody("Your Otp to Verify Email on Astra Bank is A- "+otp);
		emailBody.setToEmail(userEmailorOtp.getEmail());
		emailBody.setSubject("Astra Bank Email Verification");
		
		if(SendEmail(emailBody)==true) {
			otpRepo.save(otpVerfication);
			
			GeneralResponse response = new GeneralResponse(true, OtpVerificationId);
			return response;
		}
		else {
			GeneralResponse response = new GeneralResponse(false, "Something Went Wrong");
			return response;
		}
	}

	@Override
	public GeneralResponse verifyEmailOtp(RequestEmailorOtp userEmailorOtp) throws GeneralException {
		// TODO Auto-generated method stub
		
		// getting otp object with otp code
		OtpVerification otpVerify = otpRepo.findOtpByVerficationId(userEmailorOtp.getOtpId(), currentUser.getLoggedInUser());
		
		// if it is exits
		
		if(otpVerify==null) {
			throw new GeneralException("Invalid OTP");
		}
		
		// check if the otp is correct and it matches the account owner
		if(userEmailorOtp.getOtp().equals(otpVerify.getOtp())) {
			
			Account account = accountRepo.findAccountByUsername(currentUser.getLoggedInUser());
			
			// updating email and account status
			account.setStatus(AccountOrCardStatus.Active);
			
			// getting customer
			account.getCustomer().setEmail(otpVerify.getEmail());
			
			// saving account
			accountRepo.save(account);
			
			// saving otp module
			otpRepo.save(otpVerify);
			
			
			return new GeneralResponse(true, "OTP Verified Successfully");
			
		}
		else {
			return new GeneralResponse(false, "Invalid OTP");
		}
	}

	@Override
	public EmailBody debitEmail(String fromAccount, String toAccount, String Amount, String Mode, String toEmail) {
		// TODO Auto-generated method stub
		
		EmailBody debitEmail = new EmailBody();
		debitEmail.setBody("<body>\r\n"
				+ "    <div class=\"container\">\r\n"
				+ "        <div class=\"header\">\r\n"
				+ "            <h2>Debit Transaction Alert</h2>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"content\">\r\n"
				+ "            <p>Dear Customer,</p>\r\n"
				+ "            <p>Your Astra Bank Account("+fromAccount+") is Debited for Amount of Rs "+Amount+"</p>\r\n"
				+"              <p>Below are the transaction details</p>\r\n"
				+ "            <ul>\r\n"
				+ "                <li><strong>To Account Number: </strong>"+toAccount+"</li>\r\n"
				+ "                <li><strong>Debited Amount:</strong> Rs "+Amount+"</li>\r\n"
				+ "                <li><strong>Transaction Type:</strong> "+Mode+"</li>\r\n"
				+ "                <li><strong>Transaction Date:</strong> "+LocalDate.now()+"</li>\r\n"
				+ "            </ul>\r\n"
				+ "            <p>If this transaction is not done by you, Immediately reply to email and we will block your account </p>\r\n"
				+ "            <p>Thank you for choosing Astra Bank your Banking Partner</p>\r\n"
				+ "        </div>\r\n"
				+ "</body>");
		
		debitEmail.setSubject("Astra Bank Transaction Alert");
		debitEmail.setToEmail(toEmail);
		
		return debitEmail;
	}
	@Override
	public EmailBody creditEmail(String fromAccount, String toAccount, String Amount, String Mode, String toEmail) {
		// TODO Auto-generated method stub
		
		EmailBody creditEmail = new EmailBody();
		creditEmail.setBody("<body>\r\n"
				+ "    <div class=\"container\">\r\n"
				+ "        <div class=\"header\">\r\n"
				+ "            <h2>Credit Transaction Alert</h2>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"content\">\r\n"
				+ "            <p>Dear Customer,</p>\r\n"
				+ "            <p>Your Astra Bank Account("+toAccount+") is Credited for Amount of Rs "+Amount+"</p>\r\n"
				+"              <p>Below are the transaction details</p>\r\n"
				+ "            <ul>\r\n"
				+ "                <li><strong>From Account Number: </strong>"+fromAccount+"</li>\r\n"
				+ "                <li><strong>Debited Amount:</strong> Rs "+Amount+"</li>\r\n"
				+ "                <li><strong>Transaction Type:</strong> "+Mode+"</li>\r\n"
				+ "                <li><strong>Transaction Date:</strong> "+LocalDate.now()+"</li>\r\n"
				+ "            </ul>\r\n"
				+ "            <p>Thank you for choosing Astra Bank your Banking Partner</p>\r\n"
				+ "        </div>\r\n"
				+ "</body>");
		
		creditEmail.setSubject("Astra Bank Transaction Alert");
		creditEmail.setToEmail(toEmail);
		
		return creditEmail;
	}

}
