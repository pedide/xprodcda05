package com.xprodcda.spring.xprodcda.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.xprodcda.spring.xprodcda.domain.User;
import com.xprodcda.spring.xprodcda.domain.UserPrincipal;
import com.xprodcda.spring.xprodcda.exception.domain.EmailExistException;
import com.xprodcda.spring.xprodcda.exception.domain.NotAnImageFileException;
import com.xprodcda.spring.xprodcda.exception.domain.UserNotFoundException;
import com.xprodcda.spring.xprodcda.exception.domain.UsernameExistException;
import com.xprodcda.spring.xprodcda.repository.UserRepository;
import com.xprodcda.spring.xprodcda.service.EmailService;
import com.xprodcda.spring.xprodcda.service.LoginAttemptService;
import com.xprodcda.spring.xprodcda.service.UserService;


import lombok.RequiredArgsConstructor;

import static com.xprodcda.spring.xprodcda.enumeration.Role.*;
import static com.xprodcda.spring.xprodcda.constant.SecurityConstant.*;	



@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService{
	
	
	
	private static final String EMPTY = null;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private UserRepository userRepository;	
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	private EmailService emailService;
	/*@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	*/

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService, EmailService emailService) {
		super();		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailService = emailService;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			LOGGER.error(NO_USER_FOUND_BY_USERNAME+username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME+username); 
		}else {
			try {
				validateLoginAttempt(user);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_BY_USERNAME+username);
			
			return userPrincipal;
		}
		
	}


	private void validateLoginAttempt(User user) throws ExecutionException {
		if(user.isNotLocked()) {
			if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			}else {
				user.setNotLocked(true);
			}
		}else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
		
	}



	// Ajoute également un objet utilisateur dans la base de données, réserver au front office elle est destinée 
		// à l'ajout d'un utilisateur lorsqu'un utilisateur créé un compte dans l'application
		@Override
		public User register(String firstname, String lastname, String username, String email)   {
			try {
				validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
				User user = new User();
				String password = generatePassword();
				String encodedPassword = encodePassword(password);
				user.setUserId(generateUserId());			
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setUsername(username);
				user.setEmail(email);
				user.setJoinDate(new Date());
				user.setPassword(encodedPassword);
				user.setActive(true);
				user.setNotLocked(true);
				user.setRole(ROLE_USER.name());
				user.setAuthorities(ROLE_USER.getAuthorities());
				  user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
				userRepository.save(user);
				LOGGER.info(NEW_USER_PASSWORD + password);
				emailService.sendNewPasswordEmail(firstname, password, email);
				
				return user;

			} catch (UserNotFoundException | UsernameExistException | EmailExistException | MessagingException  e) {
				e.printStackTrace();
			}
			return null;
		}



	private String getTemporaryProfileImageUrl(String username) {
		
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH).toString();
	}
	 

	private String encodePassword(String password) {

		return passwordEncoder.encode(password);
	}

	private String generatePassword() {
		
		return RandomStringUtils.randomAlphanumeric(10);
	}



	private String generateUserId() {
		
		return RandomStringUtils.randomNumeric(10);
	}



	// validateNewUsernameAndEmail() est appelé par validateNewUsernameAndEmail() et register()
		// elle vérifie si les valeurs Username et Email appartiennent déjà à un utlisateur 
		private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
				throws UserNotFoundException, UsernameExistException, EmailExistException {

			User userByNewUsername = findUserByUsername(newUsername);
			User userByNewEmail = findUserByEmail(newEmail);

			if (StringUtils.isNotBlank(currentUsername)) {
				User currentUser = findUserByUsername(currentUsername);

				if (currentUser == null) {
					throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
				}

				if (userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())) {

					throw new UsernameExistException(USERNAME_ALREADY_EXIST);
				}

				if (userByNewEmail != null && !currentUser.getUserId().equals(userByNewEmail.getUserId())) {
					throw new EmailExistException(EMAIL_ALREADY_EXIST);
				}
				return currentUser;
			} else {

				if (userByNewUsername != null) {
					throw new UsernameExistException(USERNAME_ALREADY_EXIST + userByNewUsername);
				}

				if (userByNewEmail != null) {
					throw new EmailExistException(EMAIL_ALREADY_EXIST + currentUsername + userByNewEmail);
				}
				return null;
			}
		}



	@Override
	public List<User> getUsers() {
		
		return  userRepository.findAll();
	}



	@Override
	public User findUserByUsername(String username) {
		
		return userRepository.findUserByUsername(username);
	}



	@Override
	public User findUserByEmail(String email) {
		
		return userRepository.findUserByEmail(email);
	}



	@Override
	public User updateUser(String currentUsername, String firstname, String lastname, String username, String email,
			String role, boolean parseBoolean, boolean parseBoolean2, MultipartFile profileImage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public User addNewUser(String firstname, String lastname, String username, String email, String role,
			boolean parseBoolean, boolean parseBoolean2, MultipartFile profileImage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		
	}


/*
	public User updateUser(String currentUsername, String firstname, String lastname, String username, String email,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, NotAnImageFileException {
		
		User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);

		currentUser.setFirstname(newFirstname);
		currentUser.setLastname(newLastname);
		currentUser.setUsername(newUsername);
		currentUser.setEmail(newEmail);
		currentUser.setActive(isActive);
		currentUser.setNotLocked(isNonLocked);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());

		userRepository.save(currentUser);
		saveProfileImage(currentUser, profileImage);
		
		System.out.println("Lett username :"+newUsername );

		return currentUser;	
	}*/


/*
	@Override
	public User addNewUser(String firstname, String lastname, String username, String email, String role,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage) {
		try {
			validateNewUsernameAndEmail(EMPTY, username, email);
			User user = new User();

			String password = generatePassword();
			String encodedPassword = encodePassword(password);
			user.setUserId(generateUserId());
			user.setUserId(username);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setEmail(email);
			user.setJoinDate(new Date());
			user.setPassword(encodedPassword);
			user.setActive(isActive);
			user.setNotLocked(isNonLocked);
			user.setRole(getRoleEnumName(role).name());
			user.setAuthorities(getRoleEnumName(role).getAuthorities());
			user.setProfileImageURL(setProfileImageUrl(username));

			/*
			 * user.setProfileImageURL(getTemporaryProfileImageURL(username));
			 */			
		/*	userRepository.save(user);
			LOGGER.info(NEW_USER_PASSWORD + password);
			saveProfileImage(user, profileImage);
			System.out.println(" ALERTE authorities : " + getRoleEnumName(role).getAuthorities());

			return user;
		} catch (UserNotFoundException | UsernameExistException | EmailExistException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/




	



}
