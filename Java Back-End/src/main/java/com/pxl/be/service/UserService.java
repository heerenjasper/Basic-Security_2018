package com.pxl.be.service;

import java.util.List;

import com.pxl.be.model.User;

public interface UserService {

	User findById(Long id);
	
	User findByEmail(String email);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(Long id);
	
	void deleteAllUsers();
	
	List<User> findAllUsers();
	
	boolean doesUserExist(User user);
	
}
