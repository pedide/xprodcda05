package com.xprodcda.spring.xprodcda.constant.filter.listener;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.xprodcda.spring.xprodcda.service.LoginAttemptService;

@Component
public class AuthenticationFailureListener {
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
		super();
		this.loginAttemptService = loginAttemptService;
	} 
	
	@EventListener
	public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event)throws ExecutionException {
		
		Object principal = event.getAuthentication().getPrincipal();
	
		if(principal instanceof String) {
			String username = (String) event.getAuthentication().getPrincipal();
			loginAttemptService.addUserToLoginAttemptCache(username);
		}
	}
	

}
