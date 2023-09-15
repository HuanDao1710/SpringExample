package com.falcon.view.boomer.core.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserJWT {
	private String userId;
	private String username;
	private List<String> roles;
	private List<String> permissions;
}
