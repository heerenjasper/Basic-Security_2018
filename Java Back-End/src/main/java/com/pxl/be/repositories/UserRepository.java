package com.pxl.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pxl.be.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
