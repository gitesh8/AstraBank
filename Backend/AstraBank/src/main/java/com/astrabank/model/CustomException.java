package com.astrabank.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomException {

	public CustomException(String path, String message) {
		this.path=path;
		this.message=message;
		this.timestamp = LocalDateTime.now();
	}
	
	private String path;
	private String message;
	private LocalDateTime timestamp;
}
