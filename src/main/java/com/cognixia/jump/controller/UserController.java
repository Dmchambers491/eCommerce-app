package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidLoginPageException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	UserRepository service;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/login/email/{email}/password/{password}")
	public User validateUser(@PathVariable String email, @PathVariable String password ) throws InvalidLoginPageException, ResourceNotFoundException {
		if(!service.existsByEmail(email)) {
			throw new ResourceNotFoundException("Email is Invalid!");
		} 
		
		User user = service.findByEmail(email).get();
		if(user.getPassword().equals(password) == false) {
			throw new InvalidLoginPageException("Password is Invalid!");
		}
		return user;
	}
	
	@DeleteMapping("/delete/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
		
		Optional<User> found = service.findById(id);
		
		if(found.isPresent()) {
			service.deleteById(id);
			
			return ResponseEntity.status(200).body("User has been deleted");
			
		}else {
			return ResponseEntity.status(400).header("User id", id + "").body("User not found");
		}
	}
	
	@PutMapping("/update/user")
	public @ResponseBody String updateUser(@RequestBody User updateUser) {
		
		Optional<User> found = service.findById(updateUser.getId());
		
		if(found.isPresent()) {
			service.save(updateUser);
			return "Saved: " + updateUser.toString();
		}else {
			return "Could not update user";
		}
		
	}
	
	@PostMapping("/add/user")
	public void addUser(@RequestBody User user) {
		
		user.setId(-1L);
		
		User added = service.save(user);
		
		System.out.println("Added: " + added);
		
	}
	
	
}
