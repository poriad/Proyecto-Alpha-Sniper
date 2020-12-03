package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Comment;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CarRentalService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carRental")
@CrossOrigin
public class CarRentalController {
	
	@Autowired
	CarRentalService carRentalService;
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<CarRental>> listCarRental() {
		
		List<CarRental> listCarRental = carRentalService.listCarRental();
		
		return new ResponseEntity(listCarRental,HttpStatus.OK);
		
	}
	
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<CarRental>> listCarRentalStatus(@PathVariable Integer status) {
		
		List<CarRental> listCarRental = carRentalService.listCarRentalStatus(status);
		
		return new ResponseEntity(listCarRental,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/carRentalId/{id}")
	public ResponseEntity<CarRental> findCarRentalById(@PathVariable Long id) {
	
		CarRental carRental = carRentalService.findById(id);
		
		return new ResponseEntity(carRental,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody CarRental carRental){
		
		carRentalService.saveCarRental(carRental);
		
		return new ResponseEntity(carRental, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody CarRental carRentalDto){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		CarRental carRental = carRentalService.findById(id);
		
		carRental.setDescription(carRentalDto.getDescription());
		carRental.setLocation(carRentalDto.getLocation());
		carRental.setName(carRental.getName());
		carRental.setPrice(carRentalDto.getPrice());
		
		carRentalService.saveCarRental(carRental);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateCarRentalStatus(@PathVariable Long id, @RequestParam Integer status){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		carRentalService.updateCarRentalStatus(id, status);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusCarRentalStation/{id}")
	public ResponseEntity<?> updateStationIdCarRental(@PathVariable Long id, @RequestParam Long station){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		carRentalService.updateStationIdCarRental(id, station);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdCarRental/{id}")
	public ResponseEntity<?> updateUserIdHotel(@PathVariable Long id, @RequestParam Long userid){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		carRentalService.updateUserIdCarRental(id, userid);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!carRentalService.existById(id)) {
			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsCarRental(id);
		
		carRentalService.deleteCarRental(id);
		
		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);
		
	}
	
	
}
