package com.astrabank.exception;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import com.astrabank.model.CustomException;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<CustomException> generalException(GeneralException g,WebRequest w){
		
		CustomException response = new CustomException(w.getDescription(false),g.getMessage());
		
		return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<CustomException> JwtException(JwtException j,WebRequest w){
		
		CustomException response = new CustomException(w.getDescription(false),j.getMessage());
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<CustomException> AuthenticationException(AuthenticationException a,WebRequest w){
		
		CustomException response = new CustomException(w.getDescription(false),a.getMessage());
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomException> Exception(Exception e,WebRequest w){
		
		CustomException response = new CustomException(w.getDescription(false),e.getMessage());
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
