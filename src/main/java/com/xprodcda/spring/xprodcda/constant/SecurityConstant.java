package com.xprodcda.spring.xprodcda.constant;

import org.springframework.beans.factory.annotation.Autowired;

public class SecurityConstant {
	public static final long EXPIRATION_TIME = 432_000_000;  //5 days expressed in millisecond
	public static final String TOKEN_PREFIX = "Bearer"; // No further check for verification when getting the TOKEN
	// starting with: Bearer.

	public static final String JWT_TOKEN_HEADER ="Jwt-Token"; // JWT stands for Java Web Token

	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified ";
	public static final String GET_ARRAYS_LLC = "Get Arrays, XPROD ";  //LLC name of the compagny
	public static final String GET_ARRAYS_ADMINISTRATION = "User Management XPROD";
	public static final String AUTHORITIES ="authorities";
	public static final String FORBIDDEN_MESSAGE ="You need to log in to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You do not have the permission to access this page";
	public static final String OPTIONS_HTTP_METHOD ="OPTIONS";
	//public static final String[] PUBLIC_URLS = {"/user/login","/user/register","/user/resetpassword/**","/user/image/**"}; //URL WE DON'T WANT BLOCK
	public static final String[] PUBLIC_URLS = {"*"};	// and we allow anything comes after /**		

	public static final String FOUND_USER_BY_USERNAME = "Returning found user by username: ";
	public static final String USER_NOT_FOUND_BY_USERNAME = "User not found by username: ";
	public static final String DEFAULT_USER_IMAGE_PATH = "/user/image/profile/temp";
	@Autowired // permet d'activer l'injection automatique de dépendance
	public static final String EMAIL_ALREADY_EXIST = "Email already exist";
	public static final String USERNAME_ALREADY_EXIST = "Username already exist";
	public static final String NO_USER_FOUND_BY_USERNAME = "User not found";
	public static final String NEW_USER_PASSWORD = "New user password : ";
}
