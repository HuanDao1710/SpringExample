package com.falcon.view.boomer.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AuthenticationSuccessResponse {
	private String accessToken;
	private String refreshToken;
	private Long expiresIn;
}
