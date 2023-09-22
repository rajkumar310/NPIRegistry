package com.npi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npi.entity.User;
import com.npi.entity.UserLogin;
import com.npi.exception.DuplicateUserFoundException;
import com.npi.exception.UserNotFoundException;
import com.npi.exception.WrongPasswordException;
import com.npi.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/User")
@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class UserController {

	@Autowired
	private UserService userservices;

	@PostMapping("user_registration")
	//@PostMapping("/")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws DuplicateUserFoundException{
		// to add it is a method
		User usr = userservices.registerUser(user);
		return new ResponseEntity<>(usr, HttpStatus.OK);
	}

	@PostMapping("user_login")
	public ResponseEntity<User> userLogin(@Valid @RequestBody UserLogin user) throws UserNotFoundException , WrongPasswordException {
		User usr = userservices.login(user);
		return new ResponseEntity<User>(usr, HttpStatus.OK);
	}

	@GetMapping("get_user_by_id/{userEmailId}")
	public ResponseEntity<User> getUserByid(@Valid @PathVariable("userEmailId") String userEmailId) throws UserNotFoundException{
		User usr = userservices.getUserByEmailId(userEmailId);
		return new ResponseEntity<User>(usr, HttpStatus.OK);

	}

	@DeleteMapping("delete_user_by_id/{userEmailId}")
	public ResponseEntity<String> deleteUserById(@Valid @PathVariable("userEmailId") String userEmailId) throws UserNotFoundException{
		String message = userservices.deleteUserByemailId(userEmailId);
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}

	@PutMapping("update_user/{userEmailId}")
	public ResponseEntity<User> updateUserById(@Valid @RequestBody User user) throws UserNotFoundException {
		User usr = userservices.updateUserByEmailId(user);
		return new ResponseEntity<User>(usr, HttpStatus.OK);
	}
	
	@GetMapping("get_all_users")
	public ResponseEntity<List<User>> getAllUsers(){
		
		List<User> usr = userservices.getAllUsers();
		return new ResponseEntity<List<User>>(usr , HttpStatus.OK);
	}

}
