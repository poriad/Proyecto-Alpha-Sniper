package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Hotel;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.HotelService;
import org.iesalixar.poriad.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/hotel")
@CrossOrigin(origins = "*")
public class HotelController {

	final static Logger logger = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	HotelService hotelService;

	@Autowired
	CommentService commentService;

	@Autowired
	TripService tripService;

	// Servicio que devuelve una lista de hoteles
	@GetMapping("/list")
	public ResponseEntity<List<Hotel>> listHotel() {

		List<Hotel> listHotel = hotelService.listHotel();

		logger.info("Servicio consumido /hotel/list");

		return new ResponseEntity(listHotel, HttpStatus.OK);
	}

	// Servicio que devuelve una lista de hoteles en función de su status
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<Hotel>> listHotelsActivated(@PathVariable Integer status) {

		List<Hotel> listHotel = hotelService.listHotelsStatus(status);

		logger.info("Servicio consumido /hotel/listStatus/" + status);

		return new ResponseEntity(listHotel, HttpStatus.OK);
	}

	// Servicio que devuelve un hotel en función de su id
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/hotelId/{id}")
	public ResponseEntity<Hotel> findHotelById(@PathVariable Long id) {

		Hotel hotel = hotelService.findById(id);

		logger.info("Servicio consumido /hotel/hotelId/" + id);

		return new ResponseEntity(hotel, HttpStatus.OK);
	}

	// Servicio que crea un hotel
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PostMapping("/create")
	public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {

		hotelService.saveHotel(hotel);

		logger.info("Servicio consumido /hotel/create, con el nombre : " + hotel.getName());

		return new ResponseEntity(hotel, HttpStatus.OK);
	}

	// Servicio que actualiza un hotel
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody Hotel hotelDto) {

		if (!hotelService.existById(id)) {

			logger.error("Error al consumir el servicio /hotel/update/" + id);

			return new ResponseEntity(new Mensaje("El hotel no existe"), HttpStatus.NOT_FOUND);
		}

		Hotel hotel = hotelService.findById(id);

		hotel.setDescription(hotelDto.getDescription());
		hotel.setLocation(hotelDto.getLocation());
		hotel.setName(hotelDto.getName());
		hotel.setPhone(hotelDto.getPhone());
		hotel.setPriceDay(hotelDto.getPriceDay());
		hotel.setUrlImages(hotelDto.getUrlImages());

		hotelService.saveHotel(hotel);

		logger.info("Servicio consumido /hotel/update/" + id + ", con body de respuesta: " + hotelDto);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza el status de un hotel
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateHotelStatus(@PathVariable Long id, @RequestParam Integer status) {

		if (!hotelService.existById(id)) {

			logger.error("Error al consumir el servicio /hotel/updateStatus/" + id);

			return new ResponseEntity(new Mensaje("El hotel no existe"), HttpStatus.NOT_FOUND);
		}

		hotelService.updateHotelStatus(id, status);

		logger.info("Servicio consumido /hotel/updateStatus/" + id + ", con status: " + status);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza un hotel, relacionandolo con una estación.
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusHotelStation/{id}")
	public ResponseEntity<?> updateStationIdHotel(@PathVariable Long id, @RequestParam Long station) {

		if (!hotelService.existById(id)) {

			logger.error("Error al consumir el servicio /hotel/updateStatusHotelStation/" + id);

			return new ResponseEntity(new Mensaje("El hotel no existe"), HttpStatus.NOT_FOUND);
		}

		hotelService.updateStationIdHotel(id, station);

		logger.info("Servicio consumido /hotel/updateStatusHotelStation/" + id + ", con estación: " + station);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza un hotel, asociandolo a un usuario
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdHotel/{id}")
	public ResponseEntity<?> updateUserIdHotel(@PathVariable Long id, @RequestParam Long userid) {

		if (!hotelService.existById(id)) {

			logger.error("Error al consumir el servicio /hotel/updateUserIdHotel/" + id);

			return new ResponseEntity(new Mensaje("El hotel no existe"), HttpStatus.NOT_FOUND);
		}

		hotelService.updateUserIdHotel(id, userid);

		logger.info("Servicio consumido /hotel/updateUserIdHotel/" + id + ", con usuario: " + userid);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que borra un hotel
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteHotel(@PathVariable Long id) {

		if (!hotelService.existById(id)) {

			logger.error("Error al consumir el servicio /hotel/delete/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		commentService.deleteCommentsHotel(id);

		hotelService.deleteHotel(id);

		logger.info("Servicio consumido /hotel/delete/" + id);

		return new ResponseEntity(new Mensaje("Servicio eliminada"), HttpStatus.OK);

	}
}
