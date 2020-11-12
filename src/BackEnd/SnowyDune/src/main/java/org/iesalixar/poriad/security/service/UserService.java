package org.iesalixar.poriad.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iesalixar.poriad.security.entity.UserSnowy;
import org.iesalixar.poriad.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public Optional<UserSnowy> getByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void save(UserSnowy userSnowy) {
		userRepository.save(userSnowy);
	}
	
}
