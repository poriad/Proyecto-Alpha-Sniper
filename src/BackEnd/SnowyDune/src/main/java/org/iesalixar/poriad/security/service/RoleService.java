package org.iesalixar.poriad.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.enums.RoleName;
import org.iesalixar.poriad.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> getByRoleName(RoleName roleName){
		return roleRepository.findByRoleName(roleName);
		
	}
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
}
