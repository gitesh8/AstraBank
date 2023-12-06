package com.astrabank.responseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenResponse {

	public JwtTokenResponse() {
		this.status=true;
	}
	
	private boolean status;
	private String jwtToken;
}
