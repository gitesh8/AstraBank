package com.astrabank.responseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralResponse {

	public GeneralResponse() {
		this.status=true;
	}
	
	private boolean status;
	private String message;
}
