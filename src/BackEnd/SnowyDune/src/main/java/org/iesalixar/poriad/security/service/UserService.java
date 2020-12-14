package org.iesalixar.poriad.security.service;

import java.util.List;
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

	public Optional<UserSnowy> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public UserSnowy getByUsernameEnterprise(String username) {
		return userRepository.getByUsernameEnterprise(username);
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

	public List<UserSnowy> listUser() {
		return userRepository.findAll();
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public void deleteUserIsEnterprise(Long id) {
		userRepository.deleteUserEnterprise(id);
	}

	public boolean existById(Long id) {
		return userRepository.existsById(id);
	}

	public UserSnowy findById(Long id) {
		return userRepository.getOne(id);
	}

	public void updateUserEnterprise(Long id, int status) {
		userRepository.updateUserEnterprise(id, status);
	}

	public void updateUserStatus(Long id, int status) {
		userRepository.updateUserStatus(id, status);
	}
}
