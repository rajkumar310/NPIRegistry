package com.npi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npi.entity.User;
import com.npi.entity.UserLogin;
import com.npi.exception.DuplicateUserFoundException;
import com.npi.exception.UserNotFoundException;
import com.npi.exception.WrongPasswordException;
import com.npi.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private Set<String> setOfEmailIds = new HashSet<>();

	@Autowired
	private UserRepository userRepository;

	private String userNotFound = "User Not Found With Email Id --";

	@Override
	public User registerUser(@Valid User user) throws DuplicateUserFoundException {
		// TODO Auto-generated method stub

		List<User> list = userRepository.findAll();

		for (User c : list) {
			setOfEmailIds.add(c.getUserEmailId());
		}
		if (setOfEmailIds.contains(user.getUserEmailId())) {
			throw new DuplicateUserFoundException("User already registered with email Id -" + user.getUserEmailId());
		}
		User usr = new User(user.getUserEmailId(), user.getUserName(), user.getUserPhoneNo(), user.getUserpassword());

		return userRepository.save(usr);
	}

	@Override
	public User login(@Valid UserLogin user) throws UserNotFoundException, WrongPasswordException {
		List<User> list = userRepository.findAll();
		for (User c : list) {
			setOfEmailIds.add(c.getUserEmailId());

		}
		if (!setOfEmailIds.contains(user.getEmailId())) {
			throw new UserNotFoundException(userNotFound + user.getEmailId());
		}
		User usr = userRepository.findByUserEmailId(user.getEmailId());
		if (!usr.getUserpassword().equals(user.getPassword())) {
			throw new WrongPasswordException("Wrong password Entered..");
		}
		return usr;
	}

	@Override
	public User getUserByEmailId(@Valid String userEmailId) throws UserNotFoundException {
		User user = userRepository.findByUserEmailId(userEmailId);
		if (user == null) {
			throw new UserNotFoundException(userNotFound + userEmailId);

		}
		return user;
	}

	@Override
	public String deleteUserByemailId(@Valid String userEmailId) throws UserNotFoundException {
		User user = userRepository.findByUserEmailId(userEmailId);
		if (user == null) {
			throw new UserNotFoundException(userNotFound + userEmailId);
		}
		userRepository.delete(user);
		return "User deleted with user email-Id" + userEmailId;
	}

	@Override
	public User updateUserByEmailId(@Valid User user) throws UserNotFoundException {
		User usr = userRepository.findByUserEmailId(user.getUserEmailId());
		if (usr == null) {
			throw new UserNotFoundException(userNotFound + user.getUserEmailId());
		}

		usr.setUserName(user.getUserName());
		usr.setUserPhoneNo(user.getUserPhoneNo());
		usr.setUserpassword(user.getUserpassword());
		userRepository.save(usr);
		return usr;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
//
//	@Override
//	public User login(@Valid UserLogin user) throws UserNotFoundException, WrongPasswordException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
