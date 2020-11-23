package org.iesalixar.poriad.security.controller;

import java.util.List;
import java.util.Set;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.iesalixar.poriad.security.enums.RoleName;
import org.iesalixar.poriad.security.service.RoleService;
import org.iesalixar.poriad.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<UserSnowy>> listUser() {

		List<UserSnowy> listUsers = userService.listUser();

		return new ResponseEntity(listUsers, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getByUsername")
	public ResponseEntity<UserSnowy> getByUsernameEnterprise(@RequestParam String username) {

		UserSnowy userSnowy = userService.getByUsernameEnterprise(username);

		return new ResponseEntity(userSnowy, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserSnowy userDto) {

		if (!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);
		
		userSnowy.setFirstName(userDto.getFirstName());
		userSnowy.setLastName(userDto.getLastName());
		userSnowy.setUsername(userDto.getUsername());
		userSnowy.setEmail(userDto.getEmail());
		userSnowy.setAddress(userDto.getAddress());
		userSnowy.setNewsletter(userDto.isNewsletter());
		userSnowy.setPhone(userDto.getPhone());

		userService.save(userSnowy);

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateToEnterprise/{id}")
	public ResponseEntity<?> updateUserToEnterprise(@PathVariable Long id, @RequestBody UserSnowy userDto) {

		if (!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);
		
		userSnowy.setNomComercial(userDto.getNomComercial());
		userSnowy.setNIF(userDto.getNIF());
		userSnowy.setCNAE(userDto.getCNAE());
		userSnowy.setActivity(userDto.getActivity());
		userSnowy.setLocation(userDto.getLocation());
		userSnowy.setEnterprisePhone(userDto.getEnterprisePhone());
		userSnowy.setEnterpriseEmail(userDto.getEnterpriseEmail());
		
		userService.updateUserEnterprise(id, 2);

		userService.save(userSnowy);

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatusEnterprise/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam int status) {

		if (!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		userSnowy.setIsEnterprise(status);

		Set<Role> roles = userSnowy.getRoles();

		if (status == 1) {
			if (!roles.contains("enterprise")) {
				roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			}

		} else if(status == 0){
			roles.remove(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			userService.deleteUserIsEnterprise(id);
		}

		userSnowy.setRoles(roles);

		userService.updateUserEnterprise(id, status);

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateActiveStatus/{id}")
	public ResponseEntity<?> updateActiveUserStatus(@PathVariable Long id, @RequestParam int status) {

		if (!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		userSnowy.setIsEnterprise(status);

		Set<Role> roles = userSnowy.getRoles();

		if (status == 1) {
			if (!roles.contains("enterprise")) {
				roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			}

		} else {
			roles.remove(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
		}

		userSnowy.setRoles(roles);

		userService.updateUserEnterprise(id, status);

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("updateUserStatus/{id}")
	public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestParam int status) {

		if (!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		Set<Role> roles = null;
		
		userSnowy.setUsername(null);

		userSnowy.setRoles(roles);

		userService.updateUserStatus(id, status);

		return new ResponseEntity(new Mensaje("Usuario eliminado"), HttpStatus.OK);

	}

}
