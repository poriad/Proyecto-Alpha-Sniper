package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Hotel;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.HotelService;
import org.iesalixar.poriad.service.TripService;
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
@RequestMapping("/hotel")
@CrossOrigin
public class HotelController {
	
	@Autowired
	HotelService hotelService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	TripService tripService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Hotel>> listHotel() {
		
		List<Hotel> listHotel = hotelService.listHotel();
		
		return new ResponseEntity(listHotel,HttpStatus.OK);
	}
	
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<Hotel>> listHotelsActivated(@PathVariable Integer status) {
		
		List<Hotel> listHotel = hotelService.listHotelsStatus(status);
		
		return new ResponseEntity(listHotel,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody Hotel hotel){
		
		hotelService.saveHotel(hotel);
		
		return new ResponseEntity(hotel, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Hotel hotelDto){
		
		if(!hotelService.existById(id)) {
			return new ResponseEntity(new Mensaje("El hotel no existe"),HttpStatus.NOT_FOUND);
		}
		
		Hotel hotel = hotelService.findById(id);
		
		hotel.setDescription(hotelDto.getDescription());
		hotel.setLocation(hotelDto.getLocation());
		hotel.setName(hotelDto.getName());
		hotel.setPhone(hotelDto.getPhone());
		hotel.setPriceDay(hotelDto.getPriceDay());
		hotel.setUlrImages(hotelDto.getUlrImages());

		
		hotelService.saveHotel(hotel);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteHotel(@PathVariable Long id){
		
		if(!hotelService.existById(id)) {
			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsHotel(id);
		
		hotelService.deleteHotel(id);
		
		return new ResponseEntity(new Mensaje("Servicio eliminada"), HttpStatus.OK);
		
	}
}
