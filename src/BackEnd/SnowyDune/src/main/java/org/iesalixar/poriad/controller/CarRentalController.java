package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.CarRental;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.service.CarRentalService;
import org.iesalixar.poriad.service.CommentService;
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
@RequestMapping("/carRental")
@CrossOrigin(origins = "*")
public class CarRentalController {

	final static Logger logger = LoggerFactory.getLogger(CarRentalController.class);

	@Autowired
	CarRentalService carRentalService;

	@Autowired
	CommentService commentService;

	// Servicio que devuelve una lista de servicios CarRental
	@GetMapping("/list")
	public ResponseEntity<List<CarRental>> listCarRental() {

		List<CarRental> listCarRental = carRentalService.listCarRental();

		logger.info("Se ha consumido el servicio carRental/list");

		return new ResponseEntity(listCarRental, HttpStatus.OK);

	}

	// Servicio que devuelve una lista de servicios CarRental, filtrado por el
	// status
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<CarRental>> listCarRentalStatus(@PathVariable Integer status) {

		List<CarRental> listCarRental = carRentalService.listCarRentalStatus(status);

		logger.info("Se ha consumido el servicio carRental/listStatus/list/" + status);

		return new ResponseEntity(listCarRental, HttpStatus.OK);
	}

	// Servicio que devuelve un resultado en función del id del parámetro
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/carRentalId/{id}")
	public ResponseEntity<CarRental> findCarRentalById(@PathVariable Long id) {

		CarRental carRental = carRentalService.findById(id);

		logger.info("Se ha consumido el servicio carRental/carRentalId/" + id);

		return new ResponseEntity(carRental, HttpStatus.OK);
	}

	// Servicio que crea un negocio CarRental
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createCarRental(@RequestBody CarRental carRental) {

		carRentalService.saveCarRental(carRental);

		logger.info("Se ha consumido el servicio carRental/create con el body de respuesta: " + carRental);

		return new ResponseEntity(carRental, HttpStatus.OK);
	}

	// Servicio que actualiza un negocio CarRental
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody CarRental carRentalDto) {

		if (!carRentalService.existById(id)) {

			logger.error("Error en la actualización, servicio carRental/update/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		CarRental carRental = carRentalService.findById(id);

		carRental.setDescription(carRentalDto.getDescription());
		carRental.setLocation(carRentalDto.getLocation());
		carRental.setName(carRental.getName());
		carRental.setPrice(carRentalDto.getPrice());

		carRentalService.saveCarRental(carRental);

		logger.info(
				"Se ha consumido el servicio carRental/update/" + id + ", con el body de respuesta: " + carRentalDto);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza el status del negocio CarRental al pasado por
	// parametro
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateCarRentalStatus(@PathVariable Long id, @RequestParam Integer status) {

		if (!carRentalService.existById(id)) {

			logger.error("Error en la actualización, servicio carRental/updateStatus/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		carRentalService.updateCarRentalStatus(id, status);

		logger.info("Se ha consumido el servicio carRental/updateStatus/" + id + ", al status: " + status);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona el identificador del negocio con el identificador de
	// la estación que le corresponde
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusCarRentalStation/{id}")
	public ResponseEntity<?> updateStationIdCarRental(@PathVariable Long id, @RequestParam Long station) {

		if (!carRentalService.existById(id)) {

			logger.error("Error en la actualización, servicio carRental/updateStatusCarRentalStation/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		carRentalService.updateStationIdCarRental(id, station);

		logger.info("Se ha consumido el servicio carRental/updateStatusCarRentalStation/" + id + ", a la estación: "
				+ station);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que relaciona el identificador del negocio con el usuario del
	// negocio
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdCarRental/{id}")
	public ResponseEntity<?> updateUserIdHotel(@PathVariable Long id, @RequestParam Long userid) {

		if (!carRentalService.existById(id)) {

			logger.error("Error en la actualización, servicio carRental/updateUserIdCarRental/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		carRentalService.updateUserIdCarRental(id, userid);

		logger.info("Se ha consumido el servicio carRental/updateUserIdCarRental/" + id + ", al usuario: " + userid);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que elimina el negocio CarRental y sus comentarios asociados.
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id) {

		if (!carRentalService.existById(id)) {

			logger.error("Error en la actualización, servicio carRental/delete/" + id);

			return new ResponseEntity(new Mensaje("El comentario no existe"), HttpStatus.NOT_FOUND);
		}

		commentService.deleteCommentsCarRental(id);

		carRentalService.deleteCarRental(id);

		logger.info("Se ha consumido el servicio carRental/delete/" + id);

		return new ResponseEntity(new Mensaje("Comentario eliminada"), HttpStatus.OK);

	}

}
