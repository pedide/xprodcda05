package com.xprodcda.spring.xprodcda.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xprodcda.spring.xprodcda.domain.User;

public interface UserService {
	
	User register(String firstName, String lastName, String username, String email);
	
	List<User> getUsers();
	
	User findUserByUsername(String username);
	
	User findUserByEmail(String email);

	User updateUser(String currentUsername, String firstname, String lastname, String username, String email,
			String role, boolean parseBoolean, boolean parseBoolean2, MultipartFile profileImage);

	User addNewUser(String firstname, String lastname, String username, String email, String role, boolean parseBoolean,
			boolean parseBoolean2, MultipartFile profileImage);

	void deleteUser(long id);

}
