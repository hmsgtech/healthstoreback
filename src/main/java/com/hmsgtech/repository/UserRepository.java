package com.hmsgtech.repository;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findUserByName(String name);
}
