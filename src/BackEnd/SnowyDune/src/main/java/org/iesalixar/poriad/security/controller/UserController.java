package org.iesalixar.poriad.security.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.security.entity.UserSnowy;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<UserSnowy>> listUser() {
		
		List<UserSnowy> listUsers = userService.listUser();
		
		return new ResponseEntity(listUsers,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody UserSnowy userDto){
		
		if(!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe"),HttpStatus.NOT_FOUND);
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
		
		return new ResponseEntity(new Mensaje("Usuario actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		
		if(!userService.existById(id)) {
			return new ResponseEntity(new Mensaje("El usuario no existe no existe"), HttpStatus.NOT_FOUND);
		}
		
		userService.deleteUser(id);
		
		return new ResponseEntity(new Mensaje("Usuario eliminado"), HttpStatus.OK);
		
	}
	
}
