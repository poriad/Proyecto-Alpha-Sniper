package org.iesalixar.poriad.controller;

import java.util.List;

import org.iesalixar.poriad.entity.Classes;
import org.iesalixar.poriad.entity.Mensaje;
import org.iesalixar.poriad.entity.SkiMaterial;
import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.service.CommentService;
import org.iesalixar.poriad.service.SkiMaterialService;
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
@RequestMapping("/skiMaterial")
@CrossOrigin(origins = "*")
public class SkiMaterialController {
	
	final static Logger logger = LoggerFactory.getLogger(SkiMaterialController.class);

	@Autowired
	SkiMaterialService skiMaterialService;
	
	@Autowired
	CommentService commentService;
	
	// Servicio que devuelve una lista de negocios de material de ski
	@GetMapping("/list")
	public ResponseEntity<List<SkiMaterial>> listSkiMaterial() {
		
		List<SkiMaterial> listSkiMaterial = skiMaterialService.listSkiMaterial();
		
		logger.info("Servicio consumido /skiMaterial/list");
		
		return new ResponseEntity(listSkiMaterial,HttpStatus.OK);
	}
	
	// Servicio que devuelve un negocio de material de ski, en función de su identificador
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/skiMaterialId/{id}")
	public ResponseEntity<SkiMaterial> findSkiMaterialById(@PathVariable Long id) {
	
		SkiMaterial skiMaterial = skiMaterialService.findById(id);
		
		logger.info("Servicio consumido /skiMaterial/skiMaterialId/" + id);
		
		return new ResponseEntity(skiMaterial,HttpStatus.OK);
	}
	
	// Servicio que devuelve una lista de negocios de material de ski en función de su status
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/listStatus/{status}")
	public ResponseEntity<List<SkiMaterial>> listSkiMaterialStatus(@PathVariable Integer status) {
		
		List<SkiMaterial> listSkiMaterial = skiMaterialService.listSkiMaterialsStatus(status);
		
		logger.info("Servicio consumido /skiMaterial/listStatus/" + status);
		
		return new ResponseEntity(listSkiMaterial,HttpStatus.OK);
	}
	
	// Servicio que crea un negocio de material de ski
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createSkiMaterial(@RequestBody SkiMaterial skiMaterial){
		
		skiMaterialService.saveSkiMaterial(skiMaterial);
		
		logger.info("Servicio consumido /skiMaterial/create, con body de respuesta " + skiMaterial);
		
		return new ResponseEntity(skiMaterial, HttpStatus.OK);
	}
	
	// Servicio que actualiza un negocio de material de ski
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSkiMaterial(@PathVariable Long id, @RequestBody SkiMaterial skiMaterialDto){
		
		if(!skiMaterialService.existById(id)) {
			
			logger.error("El servicio ha devuelto un error /skiMaterial/update/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		SkiMaterial skiMaterial = skiMaterialService.findById(id);
		
		skiMaterial.setDescription(skiMaterialDto.getDescription());
		skiMaterial.setLocation(skiMaterialDto.getLocation());
		skiMaterial.setName(skiMaterialDto.getName());
		skiMaterial.setPhone(skiMaterialDto.getPhone());
		skiMaterial.setPriceDay(skiMaterialDto.getPriceDay());
		skiMaterial.setUrlImages(skiMaterialDto.getUrlImages());
		
		skiMaterialService.saveSkiMaterial(skiMaterial);
		
		logger.info("Servicio consumido /skiMaterial/update/" + id +", con body de respuesta " + skiMaterialDto);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
	}
	
	// Servicio que actualiza el status de un negocio de material de ski
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateSkiMaterialStatus(@PathVariable Long id, @RequestParam Integer status){
		
		if(!skiMaterialService.existById(id)) {
			
			logger.error("El servicio ha devuelto un error /skiMaterial/updateStatus/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateSkiMaterialStatus(id, status);
		
		logger.info("Servicio consumido /skiMaterial/updateStatus/" + id +", con status " + status);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}

	// Servicio que actualiza el status de un negocio de material de ski relacionandolo con una estación
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateStatusSkiMaterialStation/{id}")
	public ResponseEntity<?> updateStationIdClasses(@PathVariable Long id, @RequestParam Long station){
		
		if(!skiMaterialService.existById(id)) {
			
			logger.error("El servicio ha devuelto un error /skiMaterial/updateStatusSkiMaterialStation/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateStationIdSkiMaterial(id, station);
		
		logger.info("Servicio consumido /skiMaterial/updateStatusSkiMaterialStation/" + id +", con estación " + station);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que actualiza el status de un negocio de material de ski relacionandolo con un usuario
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/updateUserIdSkiMaterial/{id}")
	public ResponseEntity<?> updateUserIdSkiMaterial(@PathVariable Long id, @RequestParam Long userid){
		
		if(!skiMaterialService.existById(id)) {
			
			logger.error("El servicio ha devuelto un error /skiMaterial/updateUserIdSkiMaterial/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"),HttpStatus.NOT_FOUND);
		}
		
		skiMaterialService.updateUserIdSkiMaterial(id, userid);
		
		logger.info("Servicio consumido /skiMaterial/updateUserIdSkiMaterial/" + id +", con usuario " + userid);
		
		return new ResponseEntity(new Mensaje("Servicio actualizado correctamente"),HttpStatus.OK);
		
	}
	
	// Servicio que borra un negocio de material de ski
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable Long id){
		
		if(!skiMaterialService.existById(id)) {
			
			logger.error("El servicio ha devuelto un error /skiMaterial/delete/" + id);
			
			return new ResponseEntity(new Mensaje("El servicio no existe"), HttpStatus.NOT_FOUND);
		}
		
		commentService.deleteCommentsSkiMaterial(id);
		
		skiMaterialService.deleteSkiMaterial(id);
		
		logger.info("Servicio consumido /skiMaterial/updateUserIdSkiMaterial/" + id);
				
		return new ResponseEntity(new Mensaje("Servicio eliminado"), HttpStatus.OK);
		
	}
	
}
