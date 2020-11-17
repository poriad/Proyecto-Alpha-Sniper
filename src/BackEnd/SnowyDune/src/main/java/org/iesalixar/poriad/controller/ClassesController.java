package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.ClassesService;
import org.iesalixar.poriad.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classes")
@CrossOrigin
public class ClassesController {
	
	@Autowired
	ClassesService classesService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Classes>> listClasses() {
		
		List<Classes> listClasses = classesService.listComment();
		
		return new ResponseEntity(listClasses,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody Classes classes){
		
		classesService.saveClasses(classes);
		
		return new ResponseEntity(classes, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Classes classesDto){
		
		if(!classesService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		Classes classes = classesService.findById(id);
		
		classes.setDescription(classesDto.getDescription());
		classes.setLocation(classesDto.getLocation());
		classes.setName(classesDto.getName());
		classes.setPhone(classesDto.getName());
		classes.setPriceHour(classesDto.getPriceHour());
		classes.setUrlImages(classesDto.getUrlImages());

		
		classesService.saveClasses(classes);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!classesService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}
		
		classesService.deleteStation(id);
		
		return new ResponseEntity(new Mensaje("Servicio eliminado"), HttpStatus.OK);
		
	}

}
