package com.pxl.be.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pxl.be.model.User;
import com.pxl.be.repositories.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	public User findByName(String email) {
		return userRepository.findByEmail(email);
	}

    public void saveUser(User user) {
        userRepository.save(user);
    }
 
    public void updateUser(User user){
        saveUser(user);
    }
 
    public void deleteUserById(Long id){
        userRepository.delete(id);
    }
 
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
 
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
 
	@Override
	public boolean doesUserExist(User user) {
        return findByName(user.getEmail()) != null;
	}

}
