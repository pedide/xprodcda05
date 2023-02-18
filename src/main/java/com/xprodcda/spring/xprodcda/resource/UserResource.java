package com.xprodcda.spring.xprodcda.resource;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xprodcda.spring.xprodcda.domain.HttpResponse;
import com.xprodcda.spring.xprodcda.domain.User;
import com.xprodcda.spring.xprodcda.domain.UserPrincipal;
import com.xprodcda.spring.xprodcda.exception.domain.EmailExistException;
import com.xprodcda.spring.xprodcda.exception.domain.EmailNotFoundException;
import com.xprodcda.spring.xprodcda.exception.domain.ExceptionHandling;
import com.xprodcda.spring.xprodcda.exception.domain.NotAnImageFileException;
import com.xprodcda.spring.xprodcda.exception.domain.UserNotFoundException;
import com.xprodcda.spring.xprodcda.exception.domain.UsernameExistException;
import com.xprodcda.spring.xprodcda.service.UserService;
import com.xprodcda.spring.xprodcda.utility.JWTTokenProvider;

import static com.xprodcda.spring.xprodcda.constant.SecurityConstant.*;	

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(path = { "/", "/user" })
@CrossOrigin("*") //@CrossOrigin("*")
public class UserResource extends ExceptionHandling {
	
	private static final HttpStatus NO_CONTENT = null;
	private static final String USER_DELETED_SUCCESSFULLY = "User delete successfully";
	@Autowired
	private final UserService userService ;
	private AuthenticationManager authenticationManager;
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	public UserResource(UserService userService,
			AuthenticationManager authenticationManager,
			JWTTokenProvider jwtTokenProvider) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	

    @CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) throws UserNotFoundException {
		authenticate(user.getUsername(), user.getPassword());
		User loginUser = userService.findUserByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	}

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}
	private void authenticate(String username, String password) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@GetMapping("/register")
	//@GetMapping
    public ResponseEntity<List<User>>getUser() {
        return ResponseEntity.ok(userService.getUsers());
    }
    
    @PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException {
		User newUser = userService.register(user.getFirstname(), user.getLastname(), user.getUsername(),
				user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	

	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("role") String role, @RequestParam("isActive") String isActive,
			@RequestParam("isNonLocked") String isNonLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {

		User updateUser = userService.updateUser(currentUsername,firstName, lastName, username, email, role,
				Boolean.parseBoolean(isNonLocked),  Boolean.parseBoolean(isActive), profileImage);
		System.out.println("Here Update : " + updateUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("role") String role,
			@RequestParam("isActive") String isActive, @RequestParam("isNonLocked") String isNonLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
		User newUser = userService.addNewUser(firstName, lastName, username, email, role,
				Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	

	@GetMapping("/find/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> user = userService.getUsers();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('user:delete')")

	//@PreAuthorize("hasAnyAuthority('user: delete')")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id)
			throws EmailNotFoundException, MessagingException {
		userService.deleteUser(id);
		return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY+ id);
	}
private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		
		HttpResponse body = new HttpResponse(httpStatus.value(),httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		
		return new ResponseEntity<>(body,httpStatus);
	}
/*	@GetMapping("/home")
	public String showUser() throws EmailExistException {
		//return "application works !";
		throw new EmailExistException("This email is already taken !");
	}
	*/
	
	

}
