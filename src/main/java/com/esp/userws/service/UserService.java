package com.esp.userws.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esp.userws.model.User;
import com.esp.userws.model.UserRole;
import com.esp.userws.repository.UserRepository;

@Service
public class UserService  implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public Iterable<User> getUsers(){
		return userRepository.findAll() ;
	}
	
	public Optional<User> getUser(Integer userId) {
		return userRepository.findById(userId);
	}
	
	public User CreateUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getUserPassword());
	    user.setUserPassword(encodedPassword);
	    
		return userRepository.save(user);
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	public User updateUser(int userId, User updatedUser) {
	    User user = userRepository.findById(userId).orElse(null);
	    if (user != null) {
	        user.setUserName(updatedUser.getUserName());
	        user.setUserRole(updatedUser.getUserRole());

	        if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isEmpty()) {
	            String encryptedPassword = passwordEncoder.encode(updatedUser.getUserPassword());
	            user.setUserPassword(encryptedPassword);
	        }

	        user = userRepository.save(user);
	    }
	    return user;
	}

	

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        System.out.println("Nom : "+user.getUserName()+". Pass"+user.getUserPassword()+". role"+getAuthorities(user.getUserRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                getAuthorities(user.getUserRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }
}