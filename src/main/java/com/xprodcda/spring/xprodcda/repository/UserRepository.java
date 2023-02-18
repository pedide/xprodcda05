package com.xprodcda.spring.xprodcda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xprodcda.spring.xprodcda.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findUserByUsername(String username);
	User findUserByEmail(String email);

}
