package com.xprodcda.spring.xprodcda.exception.domain;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xprodcda.spring.xprodcda.domain.HttpResponse;

import jakarta.persistence.NoResultException;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String ACCOUNT_LOCKED ="Your account has been locked. Please contact administration";
	private static final String METHOD_IS_NOT_ALLOWED ="This request methot is not allowed on this endpoint. Please send a '%s' request";	//'%s' remplace les méthodes supportées : POST,GET,...			
	private static final String INTERNAL_SERVER_ERROR_MSG ="An error occurred while processing the request";
	private static final String INCORRECT_CREDENTIALS ="Username / password incorrect. Please try again";
	private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
	private static final String ERROR_PROCESSING_FILE ="Error occured while processing file";
	private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
	private static final String ERROR_PATH ="/error";
	
	@ExceptionHandler(DisabledException.class)
	private ResponseEntity<HttpResponse> accountDisabledException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST,ACCOUNT_DISABLED);
	}
	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<HttpResponse> badCredentialsException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST,INCORRECT_CREDENTIALS);
	}

	@ExceptionHandler(AccessDeniedException.class)
	private ResponseEntity<HttpResponse> accessDeniedException(){
		return createHttpResponse(HttpStatus.FORBIDDEN,NOT_ENOUGH_PERMISSION );
	}

	@ExceptionHandler(LockedException.class)
	private ResponseEntity<HttpResponse> lockedException(){
		return createHttpResponse(HttpStatus.UNAUTHORIZED,ACCOUNT_LOCKED);
	}	

	@ExceptionHandler(TokenExpiredException.class)
	private ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
		return createHttpResponse(HttpStatus.UNAUTHORIZED,exception.getMessage().toUpperCase());
	}
	

	@ExceptionHandler(EmailExistException.class)
	private ResponseEntity<HttpResponse> emailExistException(EmailExistException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
	}

	@ExceptionHandler(UsernameExistException.class)
	private ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
	}

	@ExceptionHandler(EmailNotFoundException.class)
	private ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED,String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<HttpResponse> internalServerErrorException(Exception exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(NoResultException.class)
	private ResponseEntity<HttpResponse> notFoundException(NoResultException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND,exception.getMessage());
	}
	
	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
		
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus, 
				httpStatus.getReasonPhrase().toUpperCase(),
				message),httpStatus); 
	} 
	
	@ExceptionHandler(IOException.class)
	private ResponseEntity<HttpResponse> iOException(IOException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(HttpClientErrorException.NotFound.class)
	private ResponseEntity<HttpResponse> notFound404(HttpStatus httpStatus, String message ){
		
		return createHttpResponse(HttpStatus.NOT_FOUND,"There is no mapping for this URL");
	}
	
	public String getErrorPath() {
		return ERROR_PATH; 
	}
	
}
