package org.iesalixar.poriad.security.controller;

import java.util.List;
import java.util.Set;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.iesalixar.poriad.security.enums.RoleName;
import org.iesalixar.poriad.security.service.RoleService;
import org.iesalixar.poriad.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	// Servicio que obtiene una lista de usuarios
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<UserSnowy>> listUser() {

		List<UserSnowy> listUsers = userService.listUser();

		logger.info("Se ha consumido el servicio /list");

		return new ResponseEntity(listUsers, HttpStatus.OK);
	}

	// Servicio que devuelve un resultado en función del nombre de usuario
	@GetMapping("/getByUsername")
	public ResponseEntity<UserSnowy> getByUsernameEnterprise(@RequestParam String username) {

		UserSnowy userSnowy = userService.getByUsernameEnterprise(username);

		logger.info("Se ha consumido el servicio /getByUsername ");

		return new ResponseEntity(userSnowy, HttpStatus.OK);
	}
	
	// Servicio que actualiza la contraseña del usuario.
		@PutMapping("/updatePassword/{id}")
		public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestParam String password) {

			if (!userService.existById(id)) {

				logger.error("El usuario no existe, con id: " + id);

				return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);

			}

			logger.info("Se ha consumido el servicio /update/" + id + " con el password: " + password);

			UserSnowy userSnowy = userService.findById(id);

			userSnowy.setPassword(passwordEncoder.encode(password));

			userService.save(userSnowy);

			logger.info("El usuario se ha modificado: " + userSnowy.getUsername());

			return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

		}

	// Servicio que actualiza los datos del usuario.
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserSnowy userDto) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);

		}

		logger.info("Se ha consumido el servicio /update/" + id + " con el body: " + userDto);

		UserSnowy userSnowy = userService.findById(id);

		logger.info("El usuario a modificar tiene los datos iniciales ");

		userSnowy.setFirstName(userDto.getFirstName());
		userSnowy.setLastName(userDto.getLastName());
		userSnowy.setUsername(userDto.getUsername());
		userSnowy.setEmail(userDto.getEmail());
		userSnowy.setAddress(userDto.getAddress());
		userSnowy.setNewsletter(userDto.isNewsletter());
		userSnowy.setPhone(userDto.getPhone());

		userService.save(userSnowy);

		logger.info("El usuario se ha modificado: " + userSnowy.getUsername());

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza al usuario, incluyendo la contraseña encriptada
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateUserSnowy/{id}")
	public ResponseEntity<?> updateUserSnowy(@PathVariable Long id, @RequestBody UserSnowy userDto) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		logger.info("Se ha consumido el servicio /updateUserSnowy/" + id + " con el body: " + userDto);

		UserSnowy userSnowy = userService.findById(id);

		logger.info("El usuario a modificar tiene los datos iniciales ");

		userSnowy.setFirstName(userDto.getFirstName());
		userSnowy.setLastName(userDto.getLastName());
		userSnowy.setUsername(userDto.getUsername());
		userSnowy.setEmail(userDto.getEmail());
		userSnowy.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userSnowy.setAddress(userDto.getAddress());
		userSnowy.setNewsletter(userDto.isNewsletter());
		userSnowy.setPhone(userDto.getPhone());

		userService.save(userSnowy);

		logger.info("El usuario se ha modificado: " + userSnowy.getUsername());

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza el estado del parámetro isEnterprise y se actualizan
	// los campos empresa del usuario
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateToEnterprise/{id}")
	public ResponseEntity<?> updateUserToEnterprise(@PathVariable Long id, @RequestBody UserSnowy userDto) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

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

		logger.info("Se ha consumido el servicio /updateToEnterprise/" + id + ", el usuario guardado es: " + userSnowy.getUsername());

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que agrega el rol de empresa al usuario
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateStatusEnterprise/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		Set<Role> roles = userSnowy.getRoles();

		if (!roles.contains("enterprise")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
		}

		userSnowy.setRoles(roles);

		userService.save(userSnowy);

		logger.info("Se ha consumido el servicio /updateStatusEnterprise/" + id + ", añadiendo el rol de empresa: "
				+ userSnowy.getRoles());

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
	}

	// Servicio que actualiza el status de isEnterprise de un usuario y le añade el
	// rol de empresa
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateActiveStatus/{id}")
	public ResponseEntity<?> updateActiveUserStatus(@PathVariable Long id, @RequestParam int status) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

			return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		userSnowy.setIsEnterprise(status);

		Set<Role> roles = userSnowy.getRoles();

		if (status == 1) {
			if (!roles.contains("enterprise")) {
				roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			}
		}

		userSnowy.setRoles(roles);

		userService.updateUserEnterprise(id, status);

		logger.info("Se ha consumido el servicio /updateActiveStatus/" + id + ", el status guardado es: " + status);

		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
	}

	// Servicio que actualiza el parametro isActive y borra el nombre de usuario y
	// roles (borrado lógico).
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("updateUserStatus/{id}")
	public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestParam int status) {

		if (!userService.existById(id)) {

			logger.error("El usuario no existe, con id: " + id);

			return new ResponseEntity(new Mensaje("El usuario no existe no existe"), HttpStatus.NOT_FOUND);
		}

		UserSnowy userSnowy = userService.findById(id);

		logger.info("Se ha consumido el servicio /updateUserStatus/" + id + ", elminando el usuario "
				+ userSnowy.getUsername());

		Set<Role> roles = null;

		userSnowy.setUsername(null);

		userSnowy.setRoles(roles);

		userService.updateUserStatus(id, status);

		return new ResponseEntity(new Mensaje("Usuario eliminado"), HttpStatus.OK);

	}

}
