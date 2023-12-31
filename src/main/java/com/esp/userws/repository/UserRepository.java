package com.esp.userws.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esp.userws.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	public Optional<User> findByUserName(String userName);
}
