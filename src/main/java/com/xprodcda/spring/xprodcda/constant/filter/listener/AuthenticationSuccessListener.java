package com.xprodcda.spring.xprodcda.constant.filter.listener;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import com.xprodcda.spring.xprodcda.domain.User;
import com.xprodcda.spring.xprodcda.service.LoginAttemptService;
@Component
public class AuthenticationSuccessListener {
	private LoginAttemptService loginAttemptService;

	@Autowired
	public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
		super();
		this.loginAttemptService = loginAttemptService;
	}
	

	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) throws ExecutionException {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof User) {
			User user = (User) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}		
	}
}
