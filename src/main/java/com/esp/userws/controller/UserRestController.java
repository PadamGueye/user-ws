package com.esp.userws.controller;

import com.esp.userws.model.User;
import com.esp.userws.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@Autowired
	UserService userService;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	
	@GetMapping("/admin")
	public Iterable<User> getAllUsers(){
		return userService.getUsers();
	}
	
	@GetMapping("/admin/{id}")
	public Optional<User> getUser(@PathVariable("id")Integer id) {
		return userService.getUser(id);
	}
	
	@PostMapping("/admin")
	public User createUser(@RequestBody User user) {
		return userService.CreateUser(user);
	}
	
	@DeleteMapping("/admin/{id}")
	public void deleteUser(@PathVariable("id")Integer id) {
		userService.deleteUser(id);
	}
	
	@PutMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping("/login")
    public User login(@RequestBody UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        
        System.out.println("username = "+userLoginRequest.getUsername());
        System.out.println("password = "+userLoginRequest.getPassword());

        return userService.authenticate(username, password);
    }
	

}
