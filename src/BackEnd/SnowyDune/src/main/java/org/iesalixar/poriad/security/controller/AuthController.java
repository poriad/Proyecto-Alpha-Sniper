package org.iesalixar.poriad.security.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.security.dto.JwtDto;
import org.iesalixar.poriad.security.dto.LoginUser;
import org.iesalixar.poriad.security.dto.NewUser;
import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.iesalixar.poriad.security.enums.RoleName;
import org.iesalixar.poriad.security.jwt.JwtProvider;
import org.iesalixar.poriad.security.service.RoleService;
import org.iesalixar.poriad.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos o Email invalido"), HttpStatus.BAD_REQUEST);

		}

		if (userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		}

		if (userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
		}

		UserSnowy userSnowy = new UserSnowy(
				newUser.getFirstName(),
				newUser.getLastName(),
				newUser.getUsername(),
				passwordEncoder.encode(newUser.getPassword()),
				newUser.getEmail(),
				newUser.getAddress(),
				newUser.isNewsletter(),
				newUser.getPhone());
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
		
		if (newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
			roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			userSnowy.setRoles(roles);

		}
		
		if (newUser.getRoles().contains("enterprise")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
			userSnowy.setRoles(roles);

		}
		
		userSnowy.setRoles(roles);
		userService.save(userSnowy);
		
		return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);

		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}

}
