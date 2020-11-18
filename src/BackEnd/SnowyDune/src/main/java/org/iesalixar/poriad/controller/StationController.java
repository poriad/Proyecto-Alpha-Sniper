package org.iesalixar.poriad.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.StationService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
@CrossOrigin
public class StationController {
	
	@Autowired
	StationService stationService;
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Station>> listStation() {
		
		List<Station> listStation = stationService.listStation();
		
		return new ResponseEntity(listStation,HttpStatus.OK);
	}
	
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<Station>> listStationStatus(@PathVariable Integer status) {
		
		List<Station> listStation = stationService.listStationStatus(status);
		
		return new ResponseEntity(listStation,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createStation(@RequestBody Station station){
		
		if (StringUtils.isBlank(station.getName())) {
			
			return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.NOT_FOUND);
			
		}
		
		stationService.saveStation(station);
		
		return new ResponseEntity(station, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Station stationDto){
		
		if(!stationService.existById(id)) {
			return new ResponseEntity(new Mensaje("La estación no existe"),HttpStatus.NOT_FOUND);
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
		
		return new ResponseEntity(new Mensaje("Producto actualizado correctamente"),HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!stationService.existById(id)) {
			return new ResponseEntity(new Mensaje("La estación no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsStation(id);
		
		stationService.deleteStation(id);
		
		return new ResponseEntity(new Mensaje("Estación eliminada"), HttpStatus.OK);
		
	}
	
	
}
