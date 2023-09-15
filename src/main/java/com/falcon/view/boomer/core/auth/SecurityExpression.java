package com.falcon.view.boomer.core.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SecurityExpression {
	@Resource(name = "userContext")
	UserContext userContext;

	public boolean hasAnyRole(String... roles) {
		Set<String> roleUser = Set.copyOf(userContext.getUser().getRoles());
		return Arrays.stream(roles).anyMatch(roleUser::contains);
	}

	public boolean hasAnyPermission(String... permissions) {
		Set<String> permissionUser = Set.copyOf(userContext.getUser().getPermissions());
		return Arrays.stream(permissions).anyMatch(permissionUser::contains);
	}
}
