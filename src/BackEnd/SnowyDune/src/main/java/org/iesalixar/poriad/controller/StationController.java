package org.iesalixar.poriad.controller;

import java.util.List;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.ForfaitService;
import org.iesalixar.poriad.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
@CrossOrigin(origins = "*")
public class StationController {

	final static Logger logger = LoggerFactory.getLogger(StationController.class);

	@Autowired
	StationService stationService;

	@Autowired
	ForfaitService forfaitService;

	@Autowired
	CommentService commentService;

	// Servicio que devuelve una lista de estaciones
	@GetMapping("/list")
	public ResponseEntity<List<Station>> listStation() {

		List<Station> listStation = stationService.listStation();

		logger.info("Servicio consumido /station/list");

		return new ResponseEntity(listStation, HttpStatus.OK);
	}

	// Servicio que devuelve una estación en función de un identificador
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/stationId/{id}")
	public ResponseEntity<Station> findStationById(@PathVariable Long id) {

		Station listStation = stationService.findById(id);

		logger.info("Servicio consumido /station/stationId/" + id);

		return new ResponseEntity(listStation, HttpStatus.OK);
	}

	// Servicio que devuelve una lista de estaciones en función de su status
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<Station>> listStationStatus(@PathVariable Integer status) {

		List<Station> listStation = stationService.listStationStatus(status);

		logger.info("Servicio consumido /station/listStatus/" + status);

		return new ResponseEntity(listStation, HttpStatus.OK);
	}

	// Servicio que crea una nueva estación
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody Station station) {

		if (stationService.existByName(station.getName())) {

			logger.error("Error no consumido por error en la respuesta /station/create");

			return new ResponseEntity(new Mensaje("Ese nombre de estación ya existe"), HttpStatus.BAD_REQUEST);
		}

		stationService.saveStation(station);

		logger.info("Servicio consumido /station/listStatus, con body de respuesta: " + station);

		return new ResponseEntity(station, HttpStatus.OK);
	}

	// Servicio que actualiza una estación
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Station stationDto) {

		if (!stationService.existById(id)) {

			logger.error("Error no consumido por error en la respuesta /station/update/" + id);

			return new ResponseEntity(new Mensaje("La estación no existe"), HttpStatus.NOT_FOUND);
		}

		Station station = stationService.findById(id);

		station.setName(stationDto.getName());
		station.setLocation(stationDto.getLocation());
		station.setCountry(stationDto.getCountry());
		station.setOpeningDate(stationDto.getOpeningDate());
		station.setClosingDate(stationDto.getClosingDate());
		station.setDescription(stationDto.getDescription());
		station.setUrlImages(stationDto.getUrlImages());

		stationService.saveStation(station);

		logger.info("Servicio consumido /station/update/" + id + ", con body de respuesta: " + stationDto);

		return new ResponseEntity(new Mensaje("Producto actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que actualiza el status de una estación
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateStationStatus(@PathVariable Long id, @RequestParam Integer status) {

		if (!stationService.existById(id)) {

			logger.error("Error no consumido por error en la respuesta /station/updateStatus/" + id);

			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}

		stationService.updateStationStatus(id, status);

		logger.info("Servicio consumido /station/update/" + id + ", con status: " + status);

		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"), HttpStatus.OK);

	}

	// Servicio que borra una estación y los comentarios asociados
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id) {

		if (!stationService.existById(id)) {

			logger.error("Error no consumido por error en la respuesta /station/delete/" + id);

			return new ResponseEntity(new Mensaje("La estación no existe"), HttpStatus.NOT_FOUND);
		}

		commentService.deleteCommentsStation(id);

		stationService.deleteStation(id);

		logger.info("Servicio consumido /station/delete/" + id);

		return new ResponseEntity(new Mensaje("Estación eliminada"), HttpStatus.OK);

	}

}
