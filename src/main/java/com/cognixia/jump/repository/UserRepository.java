package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findAll();
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
