package com.falcon.view.boomer.core.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserContext {
	private String accessToken;
	private UserJWT user;
}
